package application.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderItemNotFoundException extends RuntimeException {
    private final Long orderItemId;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public OrderItemNotFoundException(Long orderItemId) {
        super("OrderItem not found with id: " + orderItemId);
        this.orderItemId = orderItemId;
    }
}
