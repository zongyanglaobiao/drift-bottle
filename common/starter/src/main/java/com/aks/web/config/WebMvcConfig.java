package com.aks.web.config;

import com.aks.web.autoconfigure.WebProperties;
import com.aks.web.interceptor.AuthenticationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xxl
 * @since 2024/10/21
 */
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final WebProperties properties;

    private final AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration authInterceptorRegistration = registry.addInterceptor(authenticationInterceptor);
        authInterceptorRegistration.addPathPatterns(properties.getAuthorization().getInterceptPath());
        authInterceptorRegistration.excludePathPatterns(properties.getAuthorization().getWhitelist());
    }
}
