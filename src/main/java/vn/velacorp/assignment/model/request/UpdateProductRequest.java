package vn.velacorp.assignment.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateProductRequest {
    @Schema(description = "Tên sản phẩm", example = "Iphone 12")
    private String description;
    @Schema(description = "Giá sản phẩm", example = "10000000")
    private BigDecimal price;
}
