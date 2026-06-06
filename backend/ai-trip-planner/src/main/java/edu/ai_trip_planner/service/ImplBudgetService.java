package edu.ai_trip_planner.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.ai_trip_planner.dto.request.BudgetRequest;
import edu.ai_trip_planner.entities.Budget;
import edu.ai_trip_planner.entities.Trip;
import edu.ai_trip_planner.mapper.BudgetMapper;
import edu.ai_trip_planner.repository.BudgetRepository;
import edu.ai_trip_planner.repository.TripRepository;

@Service
public class ImplBudgetService implements BudgetService {

    private final BudgetRepository budgetRepository;
    private final TripRepository tripRepository;
    private final BudgetMapper budgetMapper;

    public ImplBudgetService(BudgetRepository budgetRepository, TripRepository tripRepository, BudgetMapper budgetMapper) {
        this.budgetRepository = budgetRepository;
        this.tripRepository = tripRepository;
        this.budgetMapper = budgetMapper;
    }

    @Override
    public Budget addExpense(BudgetRequest budget) {
        Trip trip = tripRepository.findById(budget.getTripId())
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + budget.getTripId()));
        Budget budgetEntity = budgetMapper.toBudget(budget);
        budgetEntity.setTrip(trip);

        return budgetRepository.save(budgetEntity);
    }

    @Override
    public double getTotalSpent(int tripId) {
        return budgetRepository.findByTripId(tripId)
                .stream()
                .mapToDouble(Budget::getAmount)
                .sum();
    }

    @Override
    public List<Budget> getBudgetSummary(int tripId) {
        return budgetRepository.findByTripId(tripId);
    }
}
