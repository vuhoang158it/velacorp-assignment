package vn.velacorp.assignment.model;

import lombok.Getter;

@Getter
public enum ERROR {
    SUCCESS(1, "SUCCESS"),
    INVALID_REQUEST(105, "invalid request"),
    SYSTEM_ERROR(99, "System is busy now. Please try again later."),
    USER_NOT_FOUND(2, "User not found"),
    INVALID_PARAM(101, "invalid param"),
    DATA_NOT_FOUND(104, "entity not found"),
    ;


    private final int code;
    private final String message;


    ERROR(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
