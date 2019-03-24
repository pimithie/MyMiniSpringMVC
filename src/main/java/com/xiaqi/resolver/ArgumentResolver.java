package com.xiaqi.resolver;

import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * argument resolver 参数解析器
 * @author xiaqi
 * @param <T> the type of parameter while is resolved 被解析的参数类型
 */
public interface ArgumentResolver {

	/**
	 * is match current argument resolver type?
	 * 	是否匹配当前参数解析器解析的类型
	 * @return if match,true,otherwise,false
	 */
	public boolean isMatch(Parameter parameter);
	
	/**
	 * resolve the method parameter 解析方法参数
	 * @return the suitable type parameter value
	 */
	public Object resolve(HttpServletRequest request,HttpServletResponse response,Parameter parameter);
	
}
