package application.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private Long userId;
    private List<OrderItemDTO> orderItems;
}