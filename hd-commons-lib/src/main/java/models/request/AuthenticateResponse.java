package models.request;

public record AuthenticateResponse(
        String token,
        String type
) {
}
