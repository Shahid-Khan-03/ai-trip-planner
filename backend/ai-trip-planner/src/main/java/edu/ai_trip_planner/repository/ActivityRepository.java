package edu.ai_trip_planner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ai_trip_planner.entities.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    List<Activity> findByDayId(int id);
    List<Activity> findByDayTripId(int tripId);
    List<Activity> findByLocation(String location);
    void deleteByDayId(int dayId);    //  added for cascade delete
}