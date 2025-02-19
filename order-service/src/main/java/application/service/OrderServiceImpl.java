package application.service;


import application.dto.OrderDTO;
import application.dto.OrderItemDTO;
import application.entity.OrderEntity;
import application.entity.OrderItem;
import application.exception.OrderNotFoundException;
import application.repository.OrderEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderEntityRepository orderEntityRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderEntityRepository orderEntityRepository, ModelMapper modelMapper) {
        this.orderEntityRepository = orderEntityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        OrderEntity order = modelMapper.map(orderDTO, OrderEntity.class);
        OrderEntity savedOrder = orderEntityRepository.save(order);
        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        OrderEntity order = orderEntityRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderEntityRepository.findAll().stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDTO> getOrdersByUser(Long userId) {
        return orderEntityRepository.findByUserId(userId).stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        OrderEntity order = orderEntityRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        modelMapper.map(orderDTO, order);

        if (orderDTO.getOrderItems() != null) {
            order.getOrderItems().clear();

            for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
                OrderItem orderItem = modelMapper.map(itemDTO, OrderItem.class);
                orderItem.setOrderEntity(order);
                order.getOrderItems().add(orderItem);
            }
        }

        OrderEntity updatedOrder = orderEntityRepository.save(order);
        return modelMapper.map(updatedOrder, OrderDTO.class);
    }

    @Override
    public void deleteOrder(Long id) {
        orderEntityRepository.deleteById(id);
    }
}
