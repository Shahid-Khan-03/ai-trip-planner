package edu.ai_trip_planner.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ai_trip_planner.dto.request.BudgetRequest;
import edu.ai_trip_planner.dto.response.APIResponse;
import edu.ai_trip_planner.entities.Budget;
import edu.ai_trip_planner.service.BudgetService;

@RestController
@RequestMapping("/budget")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<APIResponse<Budget>> addExpense(@RequestBody BudgetRequest budget) {
        Budget created = budgetService.addExpense(budget);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(APIResponse.created(created, "Expense added successfully"));
    }

    @GetMapping
    public ResponseEntity<APIResponse<Map<String, Object>>> getBudget(@RequestParam int tripId) {
        Map<String, Object> budgetSummary = Map.of(
                "totalSpent", budgetService.getTotalSpent(tripId),
                "expenses", budgetService.getBudgetSummary(tripId));

        return ResponseEntity.ok(APIResponse.success(budgetSummary, "Budget summary fetched successfully"));
    }
}
