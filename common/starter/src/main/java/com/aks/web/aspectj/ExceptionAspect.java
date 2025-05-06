package com.aks.web.aspectj;

import cn.dev33.satoken.exception.NotLoginException;
import com.aks.web.exception.WebException;
import com.aks.web.utils.response.RespEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.aks.web.utils.response.HttpCode.INTERNAL_SERVER_ERROR;

/**
 * @author xxl
 * @since 2024/11/15
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAspect {
    /**
     * 捕捉spring boot容器所有的未知异常
     */
    @ExceptionHandler(Exception.class)
    public RespEntity<?> exception(Exception exception) {
        log.error("异常信息:{}", exception.getMessage());
        if (exception instanceof WebException com) {
            return RespEntity.error(com.getCode(), com.getMessage());
        } else if (exception instanceof BindException bindException) {
            return RespEntity.error(bindException.getFieldErrors().
                    stream().
                    map(FieldError::getDefaultMessage).
                    distinct().
                    toList().
                    toString());
        }else if (exception instanceof NotLoginException loginException) {
            return RespEntity.error(loginException.getMessage());
        }
        return RespEntity.error(INTERNAL_SERVER_ERROR.message());
    }
}
