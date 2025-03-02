package by.viachaslau.api_gateway.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessages {

    MISSING_AUTH_HEADER("Missing authorization header"),
    INVALID_AUTH_HEADER_FORMAT("Invalid authorization header format"),
    UNAUTHORIZED_ACCESS("Unauthorized access to application"),
    INVALID_JWT_TOKEN("Invalid JWT token"),
    EXPIRED_JWT_TOKEN("JWT token has expired");

    private final String message;
}
