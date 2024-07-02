package vn.velacorp.assignment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.velacorp.assignment.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate orderDate;
    private String customerName;
    private String address;
    private String email;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;
    private BigDecimal totalAmount;
}
