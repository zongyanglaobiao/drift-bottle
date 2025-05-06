package com.aks.web.anno;

import java.lang.annotation.*;

/**
 * 用于存储你想记录的方法日志
 * @author xxl
 * @since 2024/9/5
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
}
