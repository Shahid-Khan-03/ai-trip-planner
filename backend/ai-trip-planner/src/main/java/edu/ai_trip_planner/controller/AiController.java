package edu.ai_trip_planner.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ai_trip_planner.dto.request.AiRequest;
import edu.ai_trip_planner.dto.response.APIResponse;
import edu.ai_trip_planner.service.AiService;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    //  No try/catch needed — GlobalExceptionHandler handles everything
    @PostMapping("/generate")
    public ResponseEntity<APIResponse<Map<String, Object>>> generateItinerary(
            @RequestBody AiRequest request) {
        return ResponseEntity.ok(APIResponse.success(
            
                aiService.generateItinerary(request),
                "Itinerary generated successfully"));
    }

    @PostMapping("/optimize")
    public ResponseEntity<APIResponse<Map<String, Object>>> optimizeItinerary(
            @RequestBody AiRequest request) {
        return ResponseEntity.ok(APIResponse.success(
                aiService.optimizeItinerary(request),
                "Itinerary optimized successfully"));
    }
}