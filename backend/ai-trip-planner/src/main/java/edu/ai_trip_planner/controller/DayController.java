package edu.ai_trip_planner.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ai_trip_planner.dto.request.DayRequest;
import edu.ai_trip_planner.dto.response.APIResponse;
import edu.ai_trip_planner.entities.Day;
import edu.ai_trip_planner.service.DayService;

@RestController
@RequestMapping("/days")
public class DayController {

    private final DayService dayService;

    public DayController(DayService dayService) {
        this.dayService = dayService;
    }

    @PostMapping
    public ResponseEntity<APIResponse<Day>> createDay(@RequestBody DayRequest request) {
        Day created = dayService.createDay(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(APIResponse.created(created, "Day created successfully"));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<Day>>> getDaysByTripId(@RequestParam int tripId) {
        return ResponseEntity.ok(APIResponse.success(
                dayService.getDaysByTripId(tripId),
                "Trip days fetched successfully"));
    }
}
