package edu.ai_trip_planner.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.ai_trip_planner.dto.request.ActivityRequest;
import edu.ai_trip_planner.entities.Activity;

@Service
public interface ActivityService {

    Activity createActivity(ActivityRequest activity);

   List<Activity>  getActivitiesByTripId(int tripId);

    List<Activity> getAllActivities();
}
