package models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RefreshTokenRequest(
       @Size(min = 16, max= 50, message = "Refresh token must not be empty")
       @NotBlank(message = "Refresh token is required")
       String refreshToken) {
}
