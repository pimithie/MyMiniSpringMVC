package com.xiaqi.HandleMethodReturnValueHandler;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * strategy interface
 * stamethod return value handler
     *   根据方法的返回值类型进行相应的处理
 * @author xiaqi
 */
public interface HandlerMethodReturnValueHandler<T> {
	
	/**
	 * can handle？
	 * 	能否处理这种返回值类型
	 */
	boolean canHandle(Class<?> clazz);
	
	/**
	 * process the specific return value
	 * 	对相应的返回值进行处理
	 */
	void handle(T returnVal,HttpServletResponse response) throws IOException;
	
}
