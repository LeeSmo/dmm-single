package com.app.dmm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法上附上此注解，可输出log信息
 * 配合基于注解的Spring AOP-LogAspect类来使用
 * 
 * <p>Title: Log</p>
 * <p>Description: 方法上附上此注解，可输出log信息</p>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
	String value() default "";
}
