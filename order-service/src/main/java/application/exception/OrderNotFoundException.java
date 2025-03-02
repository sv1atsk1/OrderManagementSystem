package application.exception;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class OrderNotFoundException extends RuntimeException {
    private final Long orderId;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public OrderNotFoundException(Long orderId) {
        super("Order not found with id: " + orderId);
        this.orderId = orderId;
    }
}


