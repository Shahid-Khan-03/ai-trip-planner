package edu.ai_trip_planner.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiRequest {

    private int tripId;
    private String preferences;
    private String specialRequests;
}
