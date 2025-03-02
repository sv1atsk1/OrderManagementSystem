package application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessages {
    CATEGORY_DTO_NULL("CategoryDTO cannot be null"),
    CATEGORY_ID_NULL("Category id cannot be null"),
    PRODUCT_DTO_NULL("ProductDTO cannot be null"),
    ID_NULL("ID cannot be null");

    private final String message;
}