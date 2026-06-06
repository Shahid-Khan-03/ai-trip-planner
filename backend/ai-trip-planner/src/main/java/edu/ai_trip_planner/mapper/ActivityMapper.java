package edu.ai_trip_planner.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import edu.ai_trip_planner.dto.request.ActivityRequest;
import edu.ai_trip_planner.entities.Activity;

@Component
public class ActivityMapper extends ModelMapper {

    public ActivityMapper() {
        super();
    }

    public Activity toActivity(ActivityRequest activityRequest) {
        Activity activity = new Activity();
        activity.setName(activityRequest.getName());
        activity.setLocation(activityRequest.getLocation());
        activity.setTime(activityRequest.getTime());
        activity.setNotes(activityRequest.getNotes());

        return activity;
    }

    public ActivityRequest toActivityDto(Activity activity) {
        ActivityRequest activityRequest = new ActivityRequest();
        activityRequest.setName(activity.getName());
        activityRequest.setLocation(activity.getLocation());
        activityRequest.setTime(activity.getTime());
        activityRequest.setNotes(activity.getNotes());
        if (activity.getDay() != null) {
            activityRequest.setDayId(activity.getDay().getId());
        }

        return activityRequest;
    }
}
