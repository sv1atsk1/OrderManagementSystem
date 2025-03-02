package application.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductNotFoundException extends RuntimeException {
    private final Long productId;
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ProductNotFoundException(Long productId) {
        super("Product not found with id: " + productId);
        this.productId = productId;
    }
}
