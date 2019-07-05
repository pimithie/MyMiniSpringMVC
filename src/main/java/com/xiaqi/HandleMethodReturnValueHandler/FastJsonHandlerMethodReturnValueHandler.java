package com.xiaqi.HandleMethodReturnValueHandler;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.xiaqi.annotation.MyService;

/**
 * converter the object type to json
 * 	转化对象为json类型
 * @author xiaqi
 */
@MyService
public class FastJsonHandlerMethodReturnValueHandler implements HandlerMethodReturnValueHandler<Object> {

	@Override
	public boolean canHandle(Class<?> clazz) {
		return true;
	}

	@Override
	public void handle(Object returnVal, HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(JSON.toJSONString(returnVal));
	}

}
