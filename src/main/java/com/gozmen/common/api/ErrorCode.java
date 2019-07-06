package com.gozmen.common.api;

public enum ErrorCode {

    UNKNOWN(-1, "unknown"),
    SUCCESS(0, "ok"),
    ILLEGAL_ARGUMENT(300, "illegal argument"),
    NOT_FOUND(404, "not found"),
    SERVER_ERROR(500, "server error"),
    NO_PERMISSION(600, "no permission"),
    ;
    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
