package application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessages {

    ORDER_ITEM_DTO_NULL("OrderItemDTO cannot be null"),
    PRODUCT_ID_NULL("Product ID cannot be null"),
    ID_NULL("ID cannot be null"),
    ORDER_DTO_NULL("OrderDTO cannot be null"),
    USER_ID_NULL("User ID cannot be null");

    private final String message;
}
