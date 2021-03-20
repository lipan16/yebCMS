package com.lx.yeb.annotation;

import java.lang.annotation.*;

/**
 * @ClassName JwtIgnore
 * @Description jwt验证忽略注解
 * @Author lipan
 * @Date 2021/3/18 10:01
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JwtIgnore{
}
