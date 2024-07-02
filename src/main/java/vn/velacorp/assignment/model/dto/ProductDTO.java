package vn.velacorp.assignment.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductDTO {
    private Long id;
    @Schema(description = "Tên sản phẩm", example = "Iphone 12")
    private String name;
    @Schema(description = "Mô tả sản phẩm", example = "Điện thoại Iphone 12")
    private String description;
    @Schema(description = "Giá sản phẩm", example = "10000000")
    private BigDecimal price;
    @Schema(description = "Số lượng sản phẩm trong kho", example = "100")
    private Integer stockQuantity;
}
