package vn.velacorp.assignment.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderProductDTO {
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
}
