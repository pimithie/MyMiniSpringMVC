package com.xiaqi.resolver;

import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaqi.annotation.MyService;

/**
 * HttpServletResponse parameter resolver
 * HttpServletResponse的参数解析器
 * @author xiaqi
 *
 */
@MyService
public class HttpServletResponseResolver implements ArgumentResolver {

	@Override
	public boolean isMatch(Parameter parameter) {
		return parameter.getType().isAssignableFrom(HttpServletResponse.class);
	}

	@Override
	public HttpServletResponse resolve(HttpServletRequest request, HttpServletResponse response,Parameter parameter) {
		return response;
	}

}
