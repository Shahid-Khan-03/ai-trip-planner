package edu.ai_trip_planner.service;

import java.util.Map;

import edu.ai_trip_planner.dto.request.AiRequest;

public interface AiService {

    Map<String, Object> generateItinerary(AiRequest request);

    Map<String, Object> optimizeItinerary(AiRequest request);

    String buildPrompt(AiRequest request);

    Map<String, Object> parseAiResponse(String aiResponse);
}
