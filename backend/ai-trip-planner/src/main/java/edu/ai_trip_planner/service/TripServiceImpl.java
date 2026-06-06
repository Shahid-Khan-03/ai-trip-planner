package edu.ai_trip_planner.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ai_trip_planner.dto.request.TripRequest;
import edu.ai_trip_planner.entities.Day;
import edu.ai_trip_planner.entities.Trip;
import edu.ai_trip_planner.entities.User;
import edu.ai_trip_planner.exception.TripNotFoundException;
import edu.ai_trip_planner.mapper.TripMapper;
import edu.ai_trip_planner.repository.ActivityRepository;
import edu.ai_trip_planner.repository.BudgetRepository;
import edu.ai_trip_planner.repository.DayRepository;
import edu.ai_trip_planner.repository.TripRepository;
import edu.ai_trip_planner.repository.UserRepository;

@Service
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final TripMapper tripMapper;
    private final DayRepository dayRepository;
    private final ActivityRepository activityRepository;
    private final BudgetRepository budgetRepository;

    public TripServiceImpl(
            TripRepository tripRepository,
            UserRepository userRepository,
            TripMapper tripMapper,
            DayRepository dayRepository,
            ActivityRepository activityRepository,
            BudgetRepository budgetRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.tripMapper = tripMapper;
        this.dayRepository = dayRepository;
        this.activityRepository = activityRepository;
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Trip createTrip(TripRequest trip) {
        User user = userRepository.findById(trip.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + trip.getUserId()));
        Trip tripEntity = tripMapper.toTrip(trip);
        tripEntity.setUser(user);
        return tripRepository.save(tripEntity);
    }

    @Override
    public List<Trip> getUserTrips(int userId) {
        return tripRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteTrip(int id) {
        if (!tripRepository.existsById(id)) {
            throw new TripNotFoundException(id);
        }

        //  Delete in correct FK order: activities → days → budget → trip
        List<Day> days = dayRepository.findByTripId(id);
        for (Day day : days) {
            activityRepository.deleteByDayId(day.getId());
        }
        dayRepository.deleteAll(days);
        budgetRepository.deleteByTripId(id);
        tripRepository.deleteById(id);
    }

    @Override
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }
}