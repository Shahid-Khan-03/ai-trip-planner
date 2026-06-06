package edu.ai_trip_planner.dto.response;

import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {

    private int status;
    private String message;
    private T data;

    public static <T> APIResponse<T> success(T data, String message) {
        return APIResponse.<T>builder()
                .status(HttpStatus.OK.value())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> APIResponse<T> created(T data, String message) {
        return APIResponse.<T>builder()
                .status(HttpStatus.CREATED.value())
                .message(message)
                .data(data)
                .build();
    }

    public static <T> APIResponse<T> error(HttpStatus status, String message) {
        return APIResponse.<T>builder()
                .status(status.value())
                .message(message)
                .data(null)
                .build();
    }
}
