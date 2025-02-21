package application.controller;

import application.dto.OrderItemDTO;
import application.service.OrderItemServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class OrderItemController {

    private final OrderItemServiceImpl orderItemServiceImpl;

    public OrderItemController(OrderItemServiceImpl orderItemServiceImpl) {
        this.orderItemServiceImpl = orderItemServiceImpl;
    }

    @PostMapping
    public ResponseEntity<OrderItemDTO> createOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
        OrderItemDTO createdOrderItem = orderItemServiceImpl.createOrderItem(orderItemDTO);
        return new ResponseEntity<>(createdOrderItem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable Long id) {
        OrderItemDTO orderItemDTO = orderItemServiceImpl.getOrderItemById(id);
        return ResponseEntity.ok(orderItemDTO);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItemDTO>> getOrderItemsByOrderId(@PathVariable Long orderId) {
        List<OrderItemDTO> orderItems = orderItemServiceImpl.getOrderItemsByOrderId(orderId);
        return ResponseEntity.ok(orderItems);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemDTO> updateOrderItem(@PathVariable Long id, @RequestBody OrderItemDTO orderItemDTO) {
        OrderItemDTO updatedOrderItem = orderItemServiceImpl.updateOrderItem(id, orderItemDTO);
        return ResponseEntity.ok(updatedOrderItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemServiceImpl.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
