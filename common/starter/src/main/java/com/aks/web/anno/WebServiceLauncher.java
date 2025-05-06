package com.aks.web.anno;


import cn.hutool.extra.spring.EnableSpringUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Indexed;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 替代@SpringBootApplication,是一个复合注解，如没有特殊要求可以直接使用这个
 * @author xxl
 * @since 2024/11/25
 */
@Import(WebServiceLauncherImportSelector.class)
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@Indexed
@EnableSpringUtil
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Order
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebServiceLauncher {

    @AliasFor(annotation = SpringBootApplication.class, attribute = "scanBasePackages")
    String[] scanBasePackages() default {};

    @AliasFor(annotation = SpringBootApplication.class, attribute = "scanBasePackageClasses")
    Class<?>[] scanBasePackageClasses() default {};

    /**
     * 动态决定是否排除 DataSourceAutoConfiguration 解决某些模块不需要数据库配置
     */
    boolean hasDatasource() default true;
}
