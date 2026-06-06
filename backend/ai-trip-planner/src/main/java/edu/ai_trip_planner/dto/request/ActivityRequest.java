package edu.ai_trip_planner.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRequest {

    private String name;
    private String location;
    private String time;
    private String notes;
    private int dayId;
}
