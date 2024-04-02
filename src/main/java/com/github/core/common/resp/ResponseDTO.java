package com.github.core.common.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.core.common.exception.AgileException;
import com.github.core.common.exception.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 响应信息主体
 *
 * @author yayee
 */
@Data
@AllArgsConstructor
public class ResponseDTO<T> {

    private Integer code;

    private String msg;

    @JsonInclude
    private T data;

    public static <T> ResponseDTO<T> ok() {
        return build(null, ErrorCode.SUCCESS.code(), ErrorCode.SUCCESS.message());
    }

    public static <T> ResponseDTO<T> ok(T data) {
        return build(data, ErrorCode.SUCCESS.code(), ErrorCode.SUCCESS.message());
    }

    public static <T> ResponseDTO<T> fail() {
        return build(null, ErrorCode.FAILED.code(), ErrorCode.FAILED.message());
    }

    public static <T> ResponseDTO<T> fail(T data) {
        return build(data, ErrorCode.FAILED.code(), ErrorCode.FAILED.message());
    }

    public static <T> ResponseDTO<T> fail(AgileException exception) {
        return build(null, exception.getErrorCode().code(), exception.getMessage());
    }

    public static <T> ResponseDTO<T> fail(AgileException exception, T data) {
        return build(data, exception.getErrorCode().code(), exception.getMessage());
    }

    public static <T> ResponseDTO<T> build(T data, Integer code, String msg) {
        return new ResponseDTO<>(code, msg, data);
    }
}

