package vn.velacorp.assignment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
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
    private String status;
    private Double totalAmount;
}
