package edu.ai_trip_planner.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.ai_trip_planner.dto.request.ActivityRequest;
import edu.ai_trip_planner.entities.Activity;
import edu.ai_trip_planner.entities.Day;
import edu.ai_trip_planner.mapper.ActivityMapper;
import edu.ai_trip_planner.repository.ActivityRepository;
import edu.ai_trip_planner.repository.DayRepository;

@Service
public class ImplActivityService implements ActivityService {

    private final ActivityRepository activityRepository;
    private final DayRepository dayRepository;
    private final ActivityMapper activityMapper;

    public ImplActivityService(
            ActivityRepository activityRepository,
            DayRepository dayRepository,
            ActivityMapper activityMapper) {
        this.activityRepository = activityRepository;
        this.dayRepository = dayRepository;
        this.activityMapper = activityMapper;
    }

    @Override
    public Activity createActivity(ActivityRequest activity) {
        Day day = dayRepository.findById(activity.getDayId())
                .orElseThrow(() -> new RuntimeException("Day not found with id: " + activity.getDayId()));
        Activity activityEntity = activityMapper.toActivity(activity);
        activityEntity.setDay(day);

        return activityRepository.save(activityEntity);
    }

    @Override
    public List<Activity> getActivitiesByTripId(int tripId) {
        return activityRepository.findByDayTripId(tripId);
    }

    @Override
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }
}
