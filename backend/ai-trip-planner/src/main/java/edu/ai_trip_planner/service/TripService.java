package edu.ai_trip_planner.service;

import java.util.List;

import edu.ai_trip_planner.dto.request.TripRequest;
import edu.ai_trip_planner.entities.Trip;

public interface TripService {

    Trip createTrip(TripRequest trip);

    List<Trip> getUserTrips(int userId);

    void deleteTrip(int id);

    List<Trip> getAllTrips();

    

}
