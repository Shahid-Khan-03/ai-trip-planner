package edu.ai_trip_planner.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayRequest {

    private int dayNumber;
    private String date;
    private int tripId;
}
