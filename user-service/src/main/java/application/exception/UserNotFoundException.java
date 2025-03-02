package application.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserNotFoundException extends RuntimeException {
    private final Long userId;
    private final LocalDateTime timestamp = LocalDateTime.now();
    public UserNotFoundException(Long userId) {
        super("User not found with id: " + userId);
        this.userId = userId;
    }
}
