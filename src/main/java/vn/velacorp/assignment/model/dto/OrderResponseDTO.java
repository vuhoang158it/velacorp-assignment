package vn.velacorp.assignment.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    @Schema(description = "yyyy-MM-dd")
    private LocalDate orderDate;
    private String customerName;
    private String address;
    private String email;
    private String phoneNumber;
    private String status;
    private BigDecimal totalAmount;
}