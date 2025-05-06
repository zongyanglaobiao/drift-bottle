package com.aks.user.conrtoller;

import com.aks.web.utils.response.RespEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jamesaks
 * @since 2025/5/6
 */
@RestController("test")
public class TestController {

    @GetMapping("hello")
    public RespEntity<String> test() {
        return RespEntity.success();
    }

}
