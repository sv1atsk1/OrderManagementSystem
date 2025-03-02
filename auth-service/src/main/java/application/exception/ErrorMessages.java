package application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessages {

    USERNAME_BLANK("Username cannot be blank"),
    USERNAME_SIZE("Username must be between 3 and 50 characters"),
    PASSWORD_BLANK("Password cannot be blank"),
    PASSWORD_SIZE("Password must be between 8 and 100 characters"),
    EMAIL_BLANK("Email cannot be blank"),
    EMAIL_INVALID("Email should be valid"),
    INVALID_ACCESS("Invalid Access"),
    INVALID_AUTH_HEADER("Invalid or missing Authorization header");

    private final String message;
}
