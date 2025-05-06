package com.aks.web.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import com.aks.web.autoconfigure.WebProperties;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 身份验证
 * @author xxl
 * @since 2024/9/14
 */
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final WebProperties properties;

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request,@Nonnull HttpServletResponse response,@Nonnull Object handler) {
        if (!properties.getAuthorization().isEnable()) {
            return true;
        }
        StpUtil.checkLogin();
        return true;
    }
}
