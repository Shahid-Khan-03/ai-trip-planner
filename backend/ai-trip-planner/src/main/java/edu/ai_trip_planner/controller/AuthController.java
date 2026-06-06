package edu.ai_trip_planner.controller;

import edu.ai_trip_planner.dto.LoginRequest;
import edu.ai_trip_planner.dto.request.RegisterRequest;
import edu.ai_trip_planner.dto.response.APIResponse;
import edu.ai_trip_planner.dto.response.AuthResponse;
import edu.ai_trip_planner.entities.User;
import edu.ai_trip_planner.service.JwtService;
import edu.ai_trip_planner.service.UserService;
import edu.ai_trip_planner.wrapperClasse.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<APIResponse<?>> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request);
            CustomUserDetails userDetails = new CustomUserDetails(user);
            String token = jwtService.generateToken(userDetails);

            AuthResponse authResponse = new AuthResponse(token, user.getEmail(), user.getName());
            return ResponseEntity.ok(APIResponse.success(authResponse, "User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(APIResponse.error(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse<?>> login(@RequestBody LoginRequest request) {
        try {
            // Authenticate user
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(), 
                    request.getPassword()
                )
            );
            CustomUserDetails userDetails = (CustomUserDetails) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(), 
                    request.getPassword()
                )
            ).getPrincipal();

            String token = jwtService.generateToken(userDetails);
 AuthResponse authResponse = new AuthResponse(
                    token,
                    userDetails.getUser().getEmail(),
                    userDetails.getUser().getName()
            );
            return ResponseEntity.ok(APIResponse.success(authResponse, "Login successful"));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(APIResponse.error(HttpStatus.UNAUTHORIZED, "Invalid email or password"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Login failed: " + e.getMessage()));
        }
    }
}