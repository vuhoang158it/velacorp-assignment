package vn.velacorp.assignment.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PagingResponse<T> extends BaseResponse<T> {
    @Schema(description = "Trang hiện tại", example = "1")
    private int currentPage;
    @Schema(description = "Tổng số bản ghi", example = "100")
    private long totalElement;
    @Schema(description = "Số bản ghi mỗi trang", example = "20")
    private int pageSize;
    @Schema(description = "Dữ liệu trả về")
    private T data;
}