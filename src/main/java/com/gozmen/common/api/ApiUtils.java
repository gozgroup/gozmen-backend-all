package com.gozmen.common.api;

import com.gozmen.common.exception.NoPermissionException;
import com.gozmen.common.exception.NotFoundException;
import com.gozmen.common.exception.ServerException;

public class ApiUtils {

    public static ApiResponse<?> getErrorResponse(Throwable tr) {
        ErrorCode errorCode;
        if (tr instanceof NotFoundException) {
            errorCode = ErrorCode.NOT_FOUND;
        } else if (tr instanceof NoPermissionException) {
            errorCode = ErrorCode.NO_PERMISSION;
        }  else if (tr instanceof IllegalArgumentException) {
            errorCode = ErrorCode.ILLEGAL_ARGUMENT;
        }  else if (tr instanceof ServerException) {
            errorCode = ErrorCode.SERVER_ERROR;
        } else {
            errorCode = ErrorCode.UNKNOWN;
        }
        return ApiResponse.error(errorCode);
    }

}
