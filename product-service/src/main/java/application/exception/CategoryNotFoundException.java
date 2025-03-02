package application.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CategoryNotFoundException extends RuntimeException {
    private final Long categoryId;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public CategoryNotFoundException(Long categoryId) {
        super("Category not found with id: " + categoryId);
        this.categoryId = categoryId;
    }
}

