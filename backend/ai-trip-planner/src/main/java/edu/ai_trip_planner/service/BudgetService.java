package edu.ai_trip_planner.service;

import java.util.List;

import edu.ai_trip_planner.dto.request.BudgetRequest;
import edu.ai_trip_planner.entities.Budget;

public interface BudgetService {

    Budget addExpense(BudgetRequest budget);

    double getTotalSpent(int tripId);

    List<Budget> getBudgetSummary(int tripId);
}
