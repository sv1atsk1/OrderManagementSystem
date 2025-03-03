package application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessages {

    USER_DTO_NULL("UserDTO cannot be null"),
    USERNAME_NULL_OR_BLANK("Username cannot be null or blank"),
    EMAIL_NULL_OR_BLANK("Email cannot be null or blank"),
    PASSWORD_NULL_OR_BLANK("Password cannot be null or blank"),
    ID_NULL("ID cannot be null"),
    INVALID_EMAIL_FORMAT("Please enter the correct email format"),
    USER_ID_NULL("User ID cannot be null");

    private final String message;
}
