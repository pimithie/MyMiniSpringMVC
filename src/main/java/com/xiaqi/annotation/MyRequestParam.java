package com.xiaqi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * RequestParam annotation 请求参数注解
 * @author xiaqi
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyRequestParam {

	/**
	 *  request parameter name 请求参数名称
	 */
	String value();
}
