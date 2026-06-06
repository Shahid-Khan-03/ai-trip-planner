
package edu.ai_trip_planner.exception;

public class TripNotFoundException extends RuntimeException {

    private final int tripId;

    public TripNotFoundException(int tripId) {
        super("Trip not found with id: " + tripId);
        this.tripId = tripId;
    }

    public int getTripId() {
        return tripId;
    }
}