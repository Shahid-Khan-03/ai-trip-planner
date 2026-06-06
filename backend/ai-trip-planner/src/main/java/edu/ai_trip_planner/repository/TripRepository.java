package edu.ai_trip_planner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ai_trip_planner.entities.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip,Integer>{

    public  List<Trip> findByUserId(int id);

  public   List<Trip> findByDestination(String destination);
}
