package com.aks.web.utils.response;

import lombok.RequiredArgsConstructor;

/**
 * @author xxl
 * @since 2024/9/5
 */
@RequiredArgsConstructor
public enum HttpCode  {
    SUCCESS(200, "请求成功"),
    BAD_REQUEST(400, "错误的请求"),
    UNAUTHORIZED(401, "未经授权"),
    FORBIDDEN(403, "禁止请求"),
    BAD_GATEWAY(502, "网络异常"),
    INTERNAL_SERVER_ERROR(500, "服务器错误");

    private final int code;
    private final String message;

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
