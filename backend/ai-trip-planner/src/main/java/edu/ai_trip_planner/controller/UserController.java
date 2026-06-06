package edu.ai_trip_planner.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ai_trip_planner.dto.request.UpdateProfileRequest;
import edu.ai_trip_planner.dto.request.UpdateProfileByEmailRequest;
import edu.ai_trip_planner.dto.request.ChangePasswordRequest;
import edu.ai_trip_planner.dto.response.APIResponse;
import edu.ai_trip_planner.entities.User;
import edu.ai_trip_planner.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/{userId}/profile")
    public ResponseEntity<APIResponse<?>> updateProfile(
            @PathVariable int userId,
            @RequestBody UpdateProfileRequest request) {
        try {
            User updatedUser = userService.updateProfile(userId, request.getName(), request.getEmail());
            return ResponseEntity.ok(APIResponse.success(Map.of(
                    "id", updatedUser.getId(),
                    "name", updatedUser.getName(),
                    "email", updatedUser.getEmail()), "Profile updated successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(APIResponse.error(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PatchMapping("/{userId}/change-password")
    public ResponseEntity<APIResponse<?>> changePassword(
            @PathVariable int userId,
            @RequestBody ChangePasswordRequest request) {
        try {
            User updatedUser = userService.changePassword(userId, request.getCurrentPassword(), request.getNewPassword());
            return ResponseEntity.ok(APIResponse.success(Map.of("id", updatedUser.getId()), "Password changed successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(APIResponse.error(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PatchMapping("/profile/by-email")
    public ResponseEntity<APIResponse<?>> updateProfileByEmail(@RequestBody UpdateProfileByEmailRequest request) {
        try {
            User updatedUser = userService.updateProfileByEmail(request.getCurrentEmail(), request.getName(), request.getEmail());
            return ResponseEntity.ok(APIResponse.success(Map.of(
                    "id", updatedUser.getId(),
                    "name", updatedUser.getName(),
                    "email", updatedUser.getEmail()), "Profile updated successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(APIResponse.error(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }
}