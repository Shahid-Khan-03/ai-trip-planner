package edu.ai_trip_planner.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import edu.ai_trip_planner.dto.request.BudgetRequest;
import edu.ai_trip_planner.entities.Budget;

@Component
public class BudgetMapper extends ModelMapper {

    public BudgetMapper() {
        super();
    }

    public Budget toBudget(BudgetRequest budgetRequest) {
        Budget budget = new Budget();
        budget.setCategory(budgetRequest.getCategory());
        budget.setAmount(budgetRequest.getAmount());

        return budget;
    }

    public BudgetRequest toBudgetDto(Budget budget) {
        BudgetRequest budgetRequest = new BudgetRequest();
        budgetRequest.setCategory(budget.getCategory());
        budgetRequest.setAmount(budget.getAmount());
        if (budget.getTrip() != null) {
            budgetRequest.setTripId(budget.getTrip().getId());
        }

        return budgetRequest;
    }
}
