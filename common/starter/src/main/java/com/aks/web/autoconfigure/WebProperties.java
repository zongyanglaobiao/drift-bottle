package com.aks.web.autoconfigure;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.stream.Stream;

/**
 * @author xxl
 * @since 2024/9/5
 */
@Getter
@ConfigurationProperties(prefix = WebProperties.PREFIX)
public class WebProperties {

    public static final String PREFIX = "web";

    private final ControllerProperties controller = new ControllerProperties();

    private final AuthorizationProperties authorization = new AuthorizationProperties();

    @Data
    public static class ControllerProperties{
        /**
         * 是否开启接口日志
         */
        private boolean logEnable = true;
    }

    @Data
    public static class AuthorizationProperties{
        /**
         *  拦截路径
         */
        private String interceptPath = "/**";

        /**
         *  白名单
         */
        private String[] whitelist = new String[]{
                "/**/doc.html",
                "/**/*.css",
                "/**/*.js",
                "/**/*.png",
                "/**/*.jpg",
                "/**/*.ico",
                "/**/v3/**"
        };

        /**
         * 是否开启接口TOKEN校验,默认为开启
         */
        private boolean enable = true;

        public void setWhitelist(String[] whitelist) {
            this.whitelist = Stream.concat(Stream.of(whitelist), Stream.of(this.whitelist)).toArray(String[]::new);
        }
    }
}
