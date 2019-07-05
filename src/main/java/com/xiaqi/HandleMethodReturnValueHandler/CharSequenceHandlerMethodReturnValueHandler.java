package com.xiaqi.HandleMethodReturnValueHandler;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.xiaqi.annotation.MyService;

/**
 * handle the charsequence type of return value
 *        处理字符串类型的返回值
 * @author xiaqi
 */
@MyService
public class CharSequenceHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler<CharSequence> {

	@Override
	public boolean canHandle(Class<?> clazz) {
		return CharSequence.class.isAssignableFrom(clazz);
	}

	@Override
	public void handle(CharSequence returnVal, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write((String)returnVal);
	}

}
