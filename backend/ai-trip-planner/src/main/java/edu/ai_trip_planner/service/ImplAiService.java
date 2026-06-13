package edu.ai_trip_planner.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ai_trip_planner.dto.request.AiRequest;
import edu.ai_trip_planner.entities.Activity;
import edu.ai_trip_planner.entities.Budget;
import edu.ai_trip_planner.entities.Day;
import edu.ai_trip_planner.entities.Trip;
import edu.ai_trip_planner.exception.AiServiceException;
import edu.ai_trip_planner.exception.TripNotFoundException;
import edu.ai_trip_planner.repository.ActivityRepository;
import edu.ai_trip_planner.repository.BudgetRepository;
import edu.ai_trip_planner.repository.DayRepository;
import edu.ai_trip_planner.repository.TripRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImplAiService implements AiService {

    private final HttpClient httpClient;
    private final TripRepository tripRepository;
    private final DayRepository dayRepository;
    private final ActivityRepository activityRepository;
    private final BudgetRepository budgetRepository;
    private final ObjectMapper objectMapper;
    private final Map<String, Map<String, Object>> itineraryCache = new ConcurrentHashMap<>();

    @Value("${gemini.api.key:}")
    private String geminiApiKey;

    @Value("${gemini.model:gemini-3.5-flash}")
    private String geminiModel;

    public ImplAiService(
            TripRepository tripRepository,
            DayRepository dayRepository,
            ActivityRepository activityRepository,
            BudgetRepository budgetRepository,
            ObjectMapper objectMapper) {
        this.tripRepository = tripRepository;
        this.dayRepository = dayRepository;
        this.activityRepository = activityRepository;
        this.budgetRepository = budgetRepository;
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Override
    public Map<String, Object> generateItinerary(AiRequest request) {
        String cacheKey = buildCacheKey("generate", request);
        Map<String, Object> cached = itineraryCache.get(cacheKey);
        if (cached != null) {
            log.info("Returning cached itinerary for tripId {}", request.getTripId());
            return new HashMap<>(cached);
        }

        String prompt = buildPrompt(request);
        log.info("Calling Gemini to generate itinerary for tripId {}", request.getTripId());
        String aiResponse = callGemini(prompt);

        Map<String, Object> itinerary = parseAiResponse(aiResponse);
        itineraryCache.put(cacheKey, new HashMap<>(itinerary));
        return itinerary;
    }

    @Override
    public Map<String, Object> optimizeItinerary(AiRequest request) {
        String cacheKey = buildCacheKey("optimize", request);
        Map<String, Object> cached = itineraryCache.get(cacheKey);
        if (cached != null) {
            log.info("Returning cached optimized itinerary for tripId {}", request.getTripId());
            return new HashMap<>(cached);
        }

        AiRequest optimizeRequest = new AiRequest(
                request.getTripId(),
                request.getPreferences(),
                "OPTIMIZATION MODE: Review the existing itinerary and improve it for " +
                "better time management, cost savings, and experience quality. " +
                "Keep the same JSON structure. " + request.getSpecialRequests());

        String prompt = buildPrompt(optimizeRequest);
        log.info("Calling Gemini to optimize itinerary for tripId {}", request.getTripId());
        String aiResponse = callGemini(prompt);

        Map<String, Object> itinerary = parseAiResponse(aiResponse);
        itineraryCache.put(cacheKey, new HashMap<>(itinerary));
        return itinerary;
    }

    @Override
    public String buildPrompt(AiRequest request) {
        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new TripNotFoundException(request.getTripId()));

        List<Day> days = dayRepository.findByTripId(request.getTripId());
        List<Activity> activities = activityRepository.findByDayTripId(request.getTripId());
        List<Budget> expenses = budgetRepository.findByTripId(request.getTripId());
        double totalSpent = expenses.stream().mapToDouble(Budget::getAmount).sum();

        return """
                Generate a complete travel roadmap as valid JSON only.
                Do not include markdown fences or explanation outside JSON.

                Required JSON structure:
                {
                  "overview": "...",
                  "destination": "...",
                  "startDate": "...",
                  "endDate": "...",
                  "roadmap": [
                    {
                      "day": 1,
                      "title": "...",
                      "morning": "...",
                      "afternoon": "...",
                      "evening": "...",
                      "food": "...",
                      "transport": "...",
                      "estimatedCost": 0,
                      "tips": "..."
                    }
                  ],
                  "budgetBreakdown": {},
                  "packingList": [],
                  "localTips": [],
                  "warnings": []
                }

                Trip: %s, %s to %s
                Budget: %.2f, Interests: %s
                Preferences: %s
                Special requests: %s
                Existing days: %s
                Existing activities: %s
                Existing expenses: %s | Total spent: %.2f
                """.formatted(
                trip.getDestination(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getBudget(),
                trip.getInterest(),
                truncate(request.getPreferences(), 150),
                truncate(request.getSpecialRequests(), 150),
                summarizeDays(days),
                summarizeActivities(activities),
                summarizeExpenses(expenses),
                totalSpent);
    }

    @Override
    public Map<String, Object> parseAiResponse(String aiResponse) {
        try {
            JsonNode root = objectMapper.readTree(aiResponse);

            if (root.has("error")) {
                String errorMsg = root.path("error").path("message")
                        .asText("Gemini API request failed");
                throw new AiServiceException(errorMsg);
            }

            JsonNode candidates = root.path("candidates");
            if (candidates.isMissingNode() || !candidates.isArray() || candidates.size() == 0) {
                throw new AiServiceException("No candidates returned from Gemini API");
            }

            String content = candidates.get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            if (content.isBlank()) {
                throw new AiServiceException("Empty response from Gemini API");
            }

            log.info("Gemini content length: {}", content.length());
            return objectMapper.readValue(content,
                    new TypeReference<Map<String, Object>>() {});

        } catch (IOException e) {
            log.error("Failed parsing AI response: {}", aiResponse);
            throw new AiServiceException("Invalid AI JSON response", e);
        }
    }

    private String callGemini(String prompt) {
        if (geminiApiKey == null || geminiApiKey.isBlank()) {
            throw new AiServiceException(
                    "Gemini API key is missing. Set GEMINI_API_KEY environment variable.");
        }

        log.info("Using Gemini model: {}", geminiModel);

        try {
            Map<String, Object> body = Map.of(
                    "contents", List.of(Map.of(
                            "parts", List.of(Map.of("text", prompt)))));

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(
                            "https://generativelanguage.googleapis.com/v1beta/models/"
                            + geminiModel + ":generateContent?key=" + geminiApiKey))
                    .timeout(Duration.ofSeconds(60))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            objectMapper.writeValueAsString(body)))
                    .build();

            HttpResponse<String> response = httpClient.send(
                    httpRequest, HttpResponse.BodyHandlers.ofString());

            log.info("Gemini raw response: {}", response.body());

            if (response.statusCode() == 429) {
                throw new AiServiceException(
                        "AI service is busy. Please wait a moment and try again.", 429);
            }

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                log.error("Gemini API error [{}]: {}", response.statusCode(), response.body());
                throw new AiServiceException(
                        "Gemini API failed with status " + response.statusCode(),
                        response.statusCode());
            }
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
    log.error("Gemini API error [{}]: {}", response.statusCode(), response.body()); // already exists
    log.error("KEY USED (first 10 chars): {}", geminiApiKey.substring(0, 10)); // ✅ add this
    throw new AiServiceException(
            "Gemini API failed with status " + response.statusCode(),
            response.statusCode());
}
            return response.body();

        } catch (AiServiceException ex) {
            throw ex;
        } catch (IOException e) {
            throw new AiServiceException("Failed to call Gemini API", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AiServiceException("Gemini API call was interrupted", e);
        }
    }

    private String truncate(String text, int maxLength) {
        if (text == null) return "none";
        return text.length() > maxLength ? text.substring(0, maxLength) + "..." : text;
    }

    private String buildCacheKey(String action, AiRequest request) {
        return action + "|"
                + request.getTripId() + "|"
                + String.valueOf(request.getPreferences()).trim() + "|"
                + String.valueOf(request.getSpecialRequests()).trim();
    }

    private String summarizeDays(List<Day> days) {
        if (days == null || days.isEmpty()) return "none";
        StringBuilder summary = new StringBuilder();
        days.stream().limit(3).forEach(day ->
                summary.append("Day ").append(day.getDayNumber())
                       .append(" on ").append(day.getDate()).append("; "));
        return summary.toString();
    }

    private String summarizeActivities(List<Activity> activities) {
        if (activities == null || activities.isEmpty()) return "none";
        StringBuilder summary = new StringBuilder();
        activities.stream().limit(3).forEach(activity -> {
            if (activity == null) return;
            summary.append(activity.getName())
                   .append(" at ").append(activity.getLocation()).append("; ");
        });
        return summary.toString();
    }

    private String summarizeExpenses(List<Budget> expenses) {
        if (expenses == null || expenses.isEmpty()) return "none";
        StringBuilder summary = new StringBuilder();
        for (Budget expense : expenses) {
            if (expense == null) continue;
            summary.append(expense.getCategory())
                   .append(": ").append(expense.getAmount()).append("; ");
        }
        return summary.toString();
    }
}