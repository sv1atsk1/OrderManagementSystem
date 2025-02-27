package application.service;

import application.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO getOrderById(Long id);

    List<OrderDTO> getAllOrders();

    List<OrderDTO> getOrdersByUser(Long userId);

    OrderDTO updateOrder(Long id, OrderDTO orderDTO);

    OrderDTO patchOrder(Long id, OrderDTO orderDTO);

    void deleteOrder(Long id);
}
