package vn.velacorp.assignment.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderProductRequest {
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
}
