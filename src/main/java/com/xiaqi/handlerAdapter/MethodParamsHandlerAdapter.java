package com.xiaqi.handlerAdapter;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * method parameter handler adapter 方法参数处理器适配器
 * @author xiaqi
 */
public interface MethodParamsHandlerAdapter {

	/**
	 * retrieve the value of parameters of the method
	 * 取得方法的参数值
	 * @param method target method object
	 * @return the method's parameters value
	 */
	public Object[] getParameters(HttpServletRequest request,HttpServletResponse response,
								  Method method,Map<String,Object> componentMapping);
	
}
