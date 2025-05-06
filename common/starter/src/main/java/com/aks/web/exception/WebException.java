package com.aks.web.exception;

import lombok.Getter;

/**
 * @author xxl
 * @since 2024/9/5
 */
@Getter
public class WebException extends ApplicationException {

    private final int code;

    public WebException(int code, String message) {
        super(message);
        this.code = code;
    }

    public WebException(String message) {
        super(message);
        this.code = 500;
    }
}
