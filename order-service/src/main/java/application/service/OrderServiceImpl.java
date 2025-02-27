package application.service;


import application.client.ProductServiceClient;
import application.client.UserServiceClient;
import application.dto.OrderDTO;
import application.dto.OrderItemDTO;
import application.entity.OrderEntity;
import application.entity.OrderItem;
import application.exception.OrderItemNotFoundException;
import application.exception.OrderNotFoundException;
import application.repository.OrderEntityRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderEntityRepository orderEntityRepository;
    private final ModelMapper modelMapper;
    private final UserServiceClient userServiceClient;
    private final ProductServiceClient productServiceClient;

    public OrderServiceImpl(OrderEntityRepository orderEntityRepository,
                            ModelMapper modelMapper,
                            UserServiceClient userServiceClient,
                            ProductServiceClient productServiceClient) {
        this.orderEntityRepository = orderEntityRepository;
        this.modelMapper = modelMapper;
        this.userServiceClient = userServiceClient;
        this.productServiceClient = productServiceClient;
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        userServiceClient.getUserById(orderDTO.getUserId());

        OrderEntity order = modelMapper.map(orderDTO, OrderEntity.class);

        if (order.getVersion() == null) {
            order.setVersion(0);
        }

        if (order.getOrderItems() != null) {
            for (OrderItem orderItem : order.getOrderItems()) {
                productServiceClient.getProductById(orderItem.getProductId());
                orderItem.setOrderEntity(order);
                orderItem.setId(null);
                if (orderItem.getVersion() == null) {
                    orderItem.setVersion(0);
                }
            }
        }

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

    @Transactional
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        OrderEntity order = orderEntityRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        userServiceClient.getUserById(orderDTO.getUserId());

        order.setOrderDate(orderDTO.getOrderDate());
        order.setUserId(orderDTO.getUserId());

        if (orderDTO.getOrderItems() != null) {
            order.getOrderItems().clear();
            for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
                productServiceClient.getProductById(itemDTO.getProductId());
                OrderItem orderItem = modelMapper.map(itemDTO, OrderItem.class);
                orderItem.setOrderEntity(order);
                orderItem.setId(null);
                if (orderItem.getVersion() == null) {
                    orderItem.setVersion(0);
                }
                order.getOrderItems().add(orderItem);
            }
        }

        OrderEntity updatedOrder = orderEntityRepository.save(order);
        return modelMapper.map(updatedOrder, OrderDTO.class);
    }

    @Override
    @Transactional
    public OrderDTO patchOrder(Long id, OrderDTO orderDTO) {
        OrderEntity order = orderEntityRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));

        if (orderDTO.getOrderDate() != null) {
            order.setOrderDate(orderDTO.getOrderDate());
        }
        if (orderDTO.getUserId() != null) {
            order.setUserId(orderDTO.getUserId());
        }

        OrderEntity updatedOrder = orderEntityRepository.save(order);
        return modelMapper.map(updatedOrder, OrderDTO.class);
    }

    @Override
    public void deleteOrder(Long id) {
        orderEntityRepository.deleteById(id);
    }
}
