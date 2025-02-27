package application.service;

import application.client.ProductServiceClient;
import application.dto.OrderItemDTO;
import application.entity.OrderItem;
import application.exception.OrderItemNotFoundException;
import application.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;
    private final ProductServiceClient productServiceClient;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, ModelMapper modelMapper, ProductServiceClient productServiceClient) {
        this.orderItemRepository = orderItemRepository;
        this.modelMapper = modelMapper;
        this.productServiceClient = productServiceClient;
    }

    @Override
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        productServiceClient.getProductById(orderItemDTO.getProductId());
        OrderItem orderItem = modelMapper.map(orderItemDTO, OrderItem.class);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return modelMapper.map(savedOrderItem, OrderItemDTO.class);
    }

    @Override
    public OrderItemDTO getOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));
        return modelMapper.map(orderItem, OrderItemDTO.class);
    }

    @Override
    public List<OrderItemDTO> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderEntityId(orderId).stream()
                .map(orderItem -> modelMapper.map(orderItem, OrderItemDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));
        productServiceClient.getProductById(orderItemDTO.getProductId());
        modelMapper.map(orderItemDTO, orderItem);
        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);
        return modelMapper.map(updatedOrderItem, OrderItemDTO.class);
    }

    @Override
    @Transactional
    public OrderItemDTO patchOrderItem(Long id, OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(id));

        if (orderItemDTO.getProductId() != null) {
            orderItem.setProductId(orderItemDTO.getProductId());
        }
        if (orderItemDTO.getQuantity() > 0) {
            orderItem.setQuantity(orderItemDTO.getQuantity());
        }

        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);
        return modelMapper.map(updatedOrderItem, OrderItemDTO.class);
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}
