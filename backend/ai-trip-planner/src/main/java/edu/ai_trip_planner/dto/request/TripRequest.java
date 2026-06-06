package edu.ai_trip_planner.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripRequest {

    private String destination;
    private String startDate;
    private String endDate;
    private double budget;
    private String interest;
    private int userId;
}
