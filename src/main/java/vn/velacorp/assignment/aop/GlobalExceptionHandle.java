package vn.velacorp.assignment.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import vn.velacorp.assignment.models.ApiException;
import vn.velacorp.assignment.models.ERROR;
import vn.velacorp.assignment.models.responses.BaseResponse;

@ControllerAdvice
public class GlobalExceptionHandle {
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandle.class);

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse<?>> handleCustomizedException(ApiException e) {
        LOGGER.error("Exception ", e);
        return new ResponseEntity<>(new BaseResponse<>(e.getCode(), e.getMessage()), HttpStatus.OK);
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<BaseResponse<?>> handleInternalException(Exception ex) {
        try {
            LOGGER.error("Exception ", ex);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new BaseResponse<>(ERROR.SYSTEM_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
