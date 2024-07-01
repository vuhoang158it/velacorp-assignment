package vn.velacorp.assignment.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {
    @Schema(description = "Tên sản phẩm", example = "Iphone 12", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    @Schema(description = "Mô tả sản phẩm", example = "Điện thoại Iphone 12")
    private String description;
    @Schema(description = "Giá sản phẩm", example = "10000000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double price;
    @Schema(description = "Số lượng sản phẩm trong kho", example = "100", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer stockQuantity;
}
