package vn.velacorp.assignment.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    @Schema(description = "yyyy-MM-dd")
    private Date orderDate;
    private String customerName;
    private String address;
    private String email;
    private String phoneNumber;
    private String status;
    private BigDecimal totalAmount;
}
