package com.aks.web.utils.response;

import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

import static com.aks.web.utils.response.HttpCode.INTERNAL_SERVER_ERROR;
import static com.aks.web.utils.response.HttpCode.SUCCESS;

/**
 * 统一响应基础类
 * @author xxl
 * @since 2023/9/16
 */
@Getter
@Accessors(chain = true)
public final class RespEntity<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -3917323953100432259L;

    /**
     * 状态码
     */
    private final int code;

    /**
     * 提示信息
     */
    private final String message;

    /**
     * 返回数据
     */
    private final T data;

    /**
     * 日志记录数据，用于AOP方便记录日志到数据库
     */
    private final Object logData;

    private RespEntity(int code, String message, T data, Object logData) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.logData = logData;
    }

    public static <T> RespEntity<T> build(int code, String message, T data, Object logData) {
        return new RespEntity<>(code, message, data, logData);
    }

    public static <T>  RespEntity<T> success(){
        return RespEntity.build(SUCCESS.code(),  SUCCESS.message(), null, null);
    }

    public static <T>  RespEntity<T> success(String message){
        return RespEntity.build(SUCCESS.code(),  message, null, null);
    }

    public static <T>  RespEntity<T> success(int code,String message){
        return RespEntity.build(code, message, null, null);
    }

    public static <T> RespEntity<T> error() {
        return RespEntity.build(INTERNAL_SERVER_ERROR.code(), INTERNAL_SERVER_ERROR.message(), null, null);
    }

    public static <T> RespEntity<T> error(String message) {
        return RespEntity.build(INTERNAL_SERVER_ERROR.code(), message, null, null);
    }

    public static <T> RespEntity<T> error(int code,String message) {
        return RespEntity.build(code, message, null, null);
    }

    @Override
    public String toString() {
        return "RespEntity{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", code=" + code +
                '}';
    }
}

