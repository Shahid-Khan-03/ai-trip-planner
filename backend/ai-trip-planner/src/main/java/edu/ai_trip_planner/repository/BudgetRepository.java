package edu.ai_trip_planner.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ai_trip_planner.entities.Budget;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Integer> {
    List<Budget> findByTripId(int tripId);
    void deleteByTripId(int tripId);  //  added
}