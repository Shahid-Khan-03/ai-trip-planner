package edu.ai_trip_planner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ai_trip_planner.entities.Day;

@Repository
public interface DayRepository extends JpaRepository<Day,Integer>{

  public  List<Day> findByTripId(int id);
}
