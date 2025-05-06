package com.aks.web.autoconfigure;

import com.aks.web.aspectj.ControllerLogAspect;
import com.aks.web.aspectj.ExceptionAspect;
import com.aks.web.config.WebMvcConfig;
import com.aks.web.handler.EntityMetaObjectHandler;
import com.aks.web.interceptor.AuthenticationInterceptor;
import com.aks.web.utils.redis.RedisUtils;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 核心自动配置类
 * @author xxl
 * @since 2024/9/5
 */
@Configuration
@Import(WebProperties.class)
@RequiredArgsConstructor
public class WebAutoConfiguration {

    private final WebProperties properties;

    @Bean
    @ConditionalOnBean(HttpServletRequest.class)
    public ControllerLogAspect controllerLogAspect(HttpServletRequest request) {
        return properties.getController().isLogEnable() ? new ControllerLogAspect(request) : null;
    }

    /**
     * 跨域配置
     */
    @ConditionalOnMissingBean
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了,这里设置2个小时
        config.setMaxAge(360000L);
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @ConditionalOnMissingBean
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @ConditionalOnMissingBean(MetaObjectHandler.class)
    @Bean
    public EntityMetaObjectHandler entityMetaObjectHandler() {
        return new EntityMetaObjectHandler();
    }

    /**
     * redis序列化相关的配置
     */
    @ConditionalOnMissingBean
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate  = new RedisTemplate<>();
        //支持事务
        redisTemplate.setEnableTransactionSupport(true);
        //json方式序列化
        Jackson2JsonRedisSerializer<Object> jackson = new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplate.setConnectionFactory(factory);
        //String方式序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //设置所有的key为string方式序列化
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //设置所有的value为jackson方式序列化
        redisTemplate.setValueSerializer(jackson);
        redisTemplate.setHashValueSerializer(jackson);
        redisTemplate.afterPropertiesSet();

        //实例化工具类
        RedisUtils.setRedisTemplate(redisTemplate);
        return redisTemplate;
    }

    @ConditionalOnMissingBean(HandlerInterceptor.class)
    @Bean
    public AuthenticationInterceptor authenticationInterceptor(WebProperties properties) {
        return new AuthenticationInterceptor(properties);
    }

    @ConditionalOnBean(AuthenticationInterceptor.class)
    @Bean
    public WebMvcConfigurer webMvcConfig(WebProperties properties, AuthenticationInterceptor authenticationInterceptor) {
        return new WebMvcConfig(properties,authenticationInterceptor);
    }

    @Bean
    public ExceptionAspect exceptionAdviceController() {
        return new ExceptionAspect();
    }
}
