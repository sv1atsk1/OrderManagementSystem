package application.service;

import application.dto.OrderItemDTO;

import java.util.List;

public interface OrderItemService {
    OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO);

    OrderItemDTO getOrderItemById(Long id);

    List<OrderItemDTO> getOrderItemsByOrderId(Long orderId);

    OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO);
    OrderItemDTO patchOrderItem(Long id, OrderItemDTO orderItemDTO);

    void deleteOrderItem(Long id);
}
