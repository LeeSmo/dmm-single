package com.app.dmm.annotation;

import java.lang.annotation.*;

/**
 * 自定义安全认证注解
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authorized {
    //仅仅是一个占位符，用于标志是否需要安全认证
    String value() default "";
}
