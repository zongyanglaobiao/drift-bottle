package com.aks.web.aspectj;

import cn.hutool.json.JSONUtil;
import com.aks.web.model.LogModel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * 接口层日志
 * @author xxl
 * @since 2024/9/5
 */
@Aspect
@RequiredArgsConstructor
@Slf4j
public class ControllerLogAspect {

    private final HttpServletRequest request;

    /**
     * 监听使用了RestController注解的类
     *
     * @param point 切点
     * @return 接口返回结果
     */
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object apiLog(ProceedingJoinPoint point) throws Throwable {
        String requestUri = request.getRequestURI();
        String requestIp = request.getRemoteAddr();
        LocalDateTime requestTime = LocalDateTime.now();
        Throwable exceptionInfo = null;
        Object proceed = null;
        try {
            proceed = point.proceed();
        } catch (Throwable e) {
            exceptionInfo = e;
            throw e;
        } finally {
            //打印日志
            new LogModel(requestUri,
                    point.getSignature().toString(),
                    Arrays.toString(point.getArgs()),
                    Objects.isNull(proceed) ? null : JSONUtil.toJsonStr(proceed),
                    Objects.isNull(exceptionInfo) ? null : exceptionInfo.toString(),
                    requestTime,
                    LocalDateTime.now(),
                    requestIp).
                    log();
        }
        return proceed;
    }
}
