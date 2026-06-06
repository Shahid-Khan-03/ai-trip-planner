package edu.ai_trip_planner.dto.request;

import lombok.Data;

@Data
public class UpdateProfileByEmailRequest {
    private String currentEmail;
    private String name;
    private String email; // new email
}
