package models.request;

import lombok.Builder;
import lombok.With;

@Builder
@With
public record AuthenticateResponse(
        String token,
        String refreshToken,
        String type
) {
}
