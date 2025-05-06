package com.aks.web.utils.asserts;

import cn.hutool.core.lang.Assert;
import com.aks.web.exception.WebException;
import com.aks.web.utils.response.HttpCode;


/**
 * 断言工具类
 *
 * @author xxl
 * @since 2023/11/29
 */
public class AssertUtils {

    public static void notNull(Object obj,String msg) throws WebException {
        Assert.notNull(obj,  () -> new WebException(HttpCode.BAD_REQUEST.code(), msg));
    }

    public static void isNull(Object obj,String msg) throws WebException {
        Assert.isNull(obj,  () -> new WebException(HttpCode.BAD_REQUEST.code(),msg));
    }

    /**
     * true就放行
     * @param condition 布尔表达式
     * @param msg 提示信息
     * @throws WebException 异常
     */
    public static void assertTrue(boolean condition,String msg) throws WebException {
        Assert.isTrue(condition, () -> new WebException(HttpCode.BAD_REQUEST.code(),msg));
    }
}
