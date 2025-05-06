package com.aks.user;

import com.aks.web.anno.WebServiceLauncher;
import org.springframework.boot.SpringApplication;

/**
 * 用户服务
 * @author jamesaks
 * @since 2025/5/6
 */
@WebServiceLauncher
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
