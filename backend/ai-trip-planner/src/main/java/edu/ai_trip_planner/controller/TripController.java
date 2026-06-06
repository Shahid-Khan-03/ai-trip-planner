package edu.ai_trip_planner.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import edu.ai_trip_planner.dto.request.TripRequest;
import edu.ai_trip_planner.dto.response.APIResponse;
import edu.ai_trip_planner.entities.Trip;
import edu.ai_trip_planner.service.TripService;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }
     @GetMapping
     public ResponseEntity<APIResponse<List<Trip>>> getTrips(@RequestParam(required = false) Integer userId) {
        if (userId != null) {
            return ResponseEntity.ok(APIResponse.success(tripService.getUserTrips(userId), "User trips fetched successfully"));
        }
        return ResponseEntity.ok(APIResponse.success(tripService.getAllTrips(), "Trips fetched successfully"));
    }

    @PostMapping
    public ResponseEntity<APIResponse<Trip>> createTrip(@RequestBody TripRequest trip) {
        Trip created = tripService.createTrip(trip);
        return ResponseEntity.status(HttpStatus.CREATED).body(APIResponse.created(created, "Trip created successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteTrip(@PathVariable int id) {
        tripService.deleteTrip(id);
        return ResponseEntity.ok(APIResponse.<Void>success(null, "Trip deleted successfully"));
    }
}