package vn.velacorp.assignment.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import vn.velacorp.assignment.model.ERROR;

@Data
public class BaseResponse<T> {
    @Schema(description = "Mã code kết quả của API", example = "1")
    private int code;
    @Schema(description = "Thông báo lỗi của API", example = "SUCCESS")
    private String message;
    private T data;

    public BaseResponse(ERROR systemError) {
        this.code = systemError.getCode();
        this.message = systemError.getMessage();
    }


    public void setCodeSuccess() {
        this.code = 1;
        this.message = "SUCCESS";
    }

    public void setCode(ERROR error) {
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public BaseResponse() {
        this.code = 1;
        this.message = "SUCCESS";
    }

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
