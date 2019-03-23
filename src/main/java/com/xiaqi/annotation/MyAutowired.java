package com.xiaqi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Autowired annotation 自动装配注解
 * @author xiaqi
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAutowired {

	/**
	 * is required? 是否必要
	 */
	boolean isRequired() default true;
}
