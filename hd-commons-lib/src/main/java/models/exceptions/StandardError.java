package models.exceptions;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;
@Getter
@Builder
public class StandardError {
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
