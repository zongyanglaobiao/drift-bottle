package com.aks.web.exception;

import lombok.Getter;

/**
 * 应用异常
 * @author xxl
 * @since 2024/9/5
 */
@Getter
public class ApplicationException extends RuntimeException {

    private final String message;

    public ApplicationException(String message) {
        super(message);
        this.message = message;
    }
}
