
package edu.ai_trip_planner.exception;

public class AiServiceException extends RuntimeException {

    private final int statusCode;

    // Generic AI failure
    public AiServiceException(String message) {
        super(message);
        this.statusCode = 503;
    }

    // When Gemini returns a specific HTTP status (e.g. 429)
    public AiServiceException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    // When an underlying exception caused the failure
    public AiServiceException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = 503;
    }

    public int getStatusCode() {
        return statusCode;
    }
}