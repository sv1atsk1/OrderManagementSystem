package application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Long id;

    @NotNull(message = "Order date cannot be null")
    private LocalDateTime orderDate;

    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID must be positive")
    private Long userId;

    @NotNull(message = "Order items cannot be null")
    @NotEmpty(message = "Order items cannot be empty")
    private List<OrderItemDTO> orderItems;
}