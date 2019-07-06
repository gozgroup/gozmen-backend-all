package com.gozmen.common.api;

public class ApiResponse<DATA> {

    public static final int CODE_SUCCESSFUL = ErrorCode.SUCCESS.getCode();

    public static final String MESSAGE_SUCCESSFUL = ErrorCode.SUCCESS.getMessage();

    private final int code;
    private final String message;

    private final DATA data;

    private ApiResponse(int code, String message, DATA data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public DATA getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public static ApiResponse<?> success() {
        return new ApiResponse<>(CODE_SUCCESSFUL, MESSAGE_SUCCESSFUL, null);
    }

    public static <DATA> ApiResponse<DATA> success(DATA data) {
        return new ApiResponse<>(CODE_SUCCESSFUL, MESSAGE_SUCCESSFUL, data);
    }

    public static ApiResponse<?> error(int code, String message) {
        if (code == CODE_SUCCESSFUL) {
            throw new IllegalArgumentException("code must be error code");
        }
        return new ApiResponse<>(code, message, null);
    }

    public static ApiResponse<?> error(ErrorCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMessage());
    }

}
