package edu.ai_trip_planner.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.ai_trip_planner.dto.request.DayRequest;
import edu.ai_trip_planner.entities.Day;
import edu.ai_trip_planner.entities.Trip;
import edu.ai_trip_planner.repository.DayRepository;
import edu.ai_trip_planner.repository.TripRepository;

@Service
public class ImplDayService implements DayService {

    private final DayRepository dayRepository;
    private final TripRepository tripRepository;

    public ImplDayService(DayRepository dayRepository, TripRepository tripRepository) {
        this.dayRepository = dayRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public Day createDay(DayRequest request) {
        Trip trip = tripRepository.findById(request.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + request.getTripId()));

        Day day = new Day();
        day.setDayNumber(request.getDayNumber());
        day.setDate(request.getDate());
        day.setTrip(trip);

        return dayRepository.save(day);
    }

    @Override
    public List<Day> getDaysByTripId(int tripId) {
        return dayRepository.findByTripId(tripId);
    }
}
