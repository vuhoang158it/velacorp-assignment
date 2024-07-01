package vn.velacorp.assignment.models.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PagingResponse<T> extends BaseResponse<T> {
    @Schema(description = "Page hiện tại", example = "1")
    private int currentPage;
    @Schema(description = "Tổng số element", example = "100")
    private int totalElement;
    @Schema(description = "Số element mỗi page", example = "20")
    private int pageSize;
    @Schema(description = "Data")
    private T data;
}