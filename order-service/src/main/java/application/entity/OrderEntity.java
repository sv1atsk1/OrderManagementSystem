package application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @Version
    private Integer version = 0;
}

