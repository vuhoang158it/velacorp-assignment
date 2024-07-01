package vn.velacorp.assignment.models;

import lombok.Getter;

@Getter
public enum ERROR {
    SUCCESS(1, "SUCCESS"),
    CONTRACT_ERROR(999, ""),
    LOGIN_FAIL(10, "Username or password is incorrect"),
    INVALID_REQUEST(105, "invalid request"),
    SYSTEM_ERROR(99, "System is busy now. Please try again later."),
    INVALID_KEY(100, "invalid api key or secret key"),

    USER_NOT_FOUND(2, "User not found"),
    USER_DISABLE(3, "Account is locked"),
    INVALID_PARAM(101, "invalid param"),
    INVALID_TOKEN(102, "Invalid token"),
    PERMISSION_DENIED(103, "No permission"),
    DATA_NOT_FOUND(104, "entity not found"),
    ;


    private final int code;
    private final String message;


    ERROR(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
