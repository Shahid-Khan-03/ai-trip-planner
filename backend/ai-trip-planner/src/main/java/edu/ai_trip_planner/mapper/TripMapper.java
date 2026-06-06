package edu.ai_trip_planner.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import edu.ai_trip_planner.dto.request.TripRequest;
import edu.ai_trip_planner.entities.Trip;

@Component
public class TripMapper extends ModelMapper {

    public TripMapper() {
        super();
    }

    public Trip toTrip(TripRequest tripRequest) {
        Trip trip = new Trip();
        trip.setDestination(tripRequest.getDestination());
        trip.setStartDate(tripRequest.getStartDate());
        trip.setEndDate(tripRequest.getEndDate());
        trip.setBudget(tripRequest.getBudget());
        trip.setInterest(tripRequest.getInterest());

        return trip;
    }

    public TripRequest toTripRequest(Trip trip) {
        TripRequest tripRequest = new TripRequest();
        tripRequest.setDestination(trip.getDestination());
        tripRequest.setStartDate(trip.getStartDate());
        tripRequest.setEndDate(trip.getEndDate());
        tripRequest.setBudget(trip.getBudget());
        tripRequest.setInterest(trip.getInterest());
        if (trip.getUser() != null) {
            tripRequest.setUserId(trip.getUser().getId());
        }

        return tripRequest;
    }
}
