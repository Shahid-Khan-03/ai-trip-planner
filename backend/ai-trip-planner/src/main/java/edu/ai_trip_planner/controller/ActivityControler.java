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

import edu.ai_trip_planner.dto.request.ActivityRequest;
import edu.ai_trip_planner.dto.response.APIResponse;
import edu.ai_trip_planner.entities.Activity;
import edu.ai_trip_planner.service.ActivityService;

@RestController
@RequestMapping("/activities")
public class ActivityControler {

    private final ActivityService activityService;

    public ActivityControler(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<APIResponse<Activity>> createActivity(@RequestBody ActivityRequest activity) {
        Activity created = activityService.createActivity(activity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(APIResponse.created(created, "Activity created successfully"));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<Activity>>> getActivities(@RequestParam(required = false) Integer tripId) {
        if (tripId != null) {
            return ResponseEntity.ok(APIResponse.success(
                    activityService.getActivitiesByTripId(tripId),
                    "Trip activities fetched successfully"));
        }

        return ResponseEntity.ok(APIResponse.success(
                activityService.getAllActivities(),
                "Activities fetched successfully"));
    }
}
