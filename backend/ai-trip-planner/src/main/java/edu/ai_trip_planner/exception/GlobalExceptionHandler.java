
package edu.ai_trip_planner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import edu.ai_trip_planner.dto.response.APIResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Trip not found
    @ExceptionHandler(TripNotFoundException.class)
    public ResponseEntity<APIResponse<Void>> handleTripNotFound(TripNotFoundException ex) {
        log.warn("Trip not found: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(APIResponse.error(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    // 403 - User doesn't own the resource
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<APIResponse<Void>> handleUnauthorized(UnauthorizedException ex) {
        log.warn("Unauthorized access: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(APIResponse.error(HttpStatus.FORBIDDEN, ex.getMessage()));
    }

    // 503 / 429 - AI API failure or rate limit
    @ExceptionHandler(AiServiceException.class)
    public ResponseEntity<APIResponse<Void>> handleAiService(AiServiceException ex) {
        log.error("AI service error [{}]: {}", ex.getStatusCode(), ex.getMessage());

        // Map Gemini's 429 → HTTP 429, everything else → 503
        HttpStatus status = ex.getStatusCode() == 429
                ? HttpStatus.TOO_MANY_REQUESTS
                : HttpStatus.SERVICE_UNAVAILABLE;

        return ResponseEntity
                .status(status)
                .body(APIResponse.error(status, ex.getMessage()));
    }

    // 400 - Validation errors (@Valid on request body)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<Void>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");

        log.warn("Validation error: {}", message);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(APIResponse.error(HttpStatus.BAD_REQUEST, message));
    }

    // 400 - Other runtime exceptions
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<APIResponse<Void>> handleRuntime(RuntimeException ex) {
        log.error("Runtime error: {}", ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(APIResponse.error(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    // 500 - Any uncaught exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<Void>> handleGeneric(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(APIResponse.error(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Something went wrong. Please try again later."));
    }
}