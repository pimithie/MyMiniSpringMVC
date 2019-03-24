package com.xiaqi.resolver;

import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaqi.annotation.MyService;

/**
 * HttpServletRequest parameter resolver
 * HttpServletRequest的参数解析器
 * @author xiaqi
 *
 */
@MyService
public class HttpServletRequestResolver implements ArgumentResolver {

	@Override
	public boolean isMatch(Parameter parameter) {
		return parameter.getType().isAssignableFrom(HttpServletRequest.class);
	}

	@Override
	public HttpServletRequest resolve(HttpServletRequest request, HttpServletResponse response,Parameter parameter) {
		return request;
	}

}
