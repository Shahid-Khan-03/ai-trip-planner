package edu.ai_trip_planner.service;

import java.util.List;

import edu.ai_trip_planner.dto.request.DayRequest;
import edu.ai_trip_planner.entities.Day;

public interface DayService {

    Day createDay(DayRequest request);

    List<Day> getDaysByTripId(int tripId);
}
