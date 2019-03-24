package com.xiaqi.resolver;

import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaqi.annotation.MyRequestParam;
import com.xiaqi.annotation.MyService;

/**
 * MyRequestParam annotation resolver MyRequestParam注解解析器
 * 
 * @author xiaqi
 *
 */
@MyService
public class MyRequestParamAnnotationResolver implements ArgumentResolver {

	@Override
	public boolean isMatch(Parameter parameter) {
		// check whether the parameter has @MyRequestParam annotation
		// 判断当前参数是否有@MyRequestParam注解
		return parameter.isAnnotationPresent(MyRequestParam.class);
	}

	@Override
	public Object resolve(HttpServletRequest request, HttpServletResponse response, Parameter parameter) {
		MyRequestParam annotation = parameter.getAnnotation(MyRequestParam.class);
		// retrieve the value of the requestParam 取得注解上的值
		String requestParam = annotation.value();
		return request.getParameter(requestParam);
	}
}
