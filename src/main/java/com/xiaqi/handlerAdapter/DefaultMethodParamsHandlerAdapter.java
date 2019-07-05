package com.xiaqi.handlerAdapter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaqi.annotation.MyService;
import com.xiaqi.resolver.ArgumentResolver;

/**
 * default MethodParamsHandlerAdapter implementation 默认的实现
 * 
 * @author xiaqi
 */
@MyService
public class DefaultMethodParamsHandlerAdapter implements MethodParamsHandlerAdapter {

	@Override
	public Object[] getParameters(HttpServletRequest request,HttpServletResponse response,
								  Method method, Map<String, Object> componentMapping) {
		List<ArgumentResolver> resolvers = getResolvers(componentMapping);
		// retrieve all parameters object of the method 取得方法的所有的参数对象
		Parameter[] parameters = method.getParameters();
		Object[] result = new Object[parameters.length];
		int paramIndex = 0;
		for (Parameter parameter : parameters) {
			for (ArgumentResolver resolver : resolvers) {
				if (resolver.isMatch(parameter)) {
					result[paramIndex++] = resolver.resolve(request, response, parameter);
					if (int.class == parameter.getType()) {
						result[paramIndex-1] = Integer.parseInt((String) result[paramIndex-1]);
					}
				}
			}
		}
		return result;
	}

	/**
	 * retrieve all argument resolvers 向容器中取得所有的参数解析器
	 * 
	 * @param componentMapping component container
	 * @return all argument resolvers
	 */
	private List<ArgumentResolver> getResolvers(Map<String, Object> componentMapping) {
		List<ArgumentResolver> result = new ArrayList<ArgumentResolver>(1 << 4);
		for (Map.Entry<String, Object> entry : componentMapping.entrySet()) {
			// retrieve all interfaces which the current component implements
			// 取得当前组件实现的所有接口
			Class<?>[] interfaces = entry.getValue().getClass().getInterfaces();
			for (Class<?> interfaceType : interfaces) {
				if (interfaceType.isAssignableFrom(ArgumentResolver.class)) {
					result.add((ArgumentResolver) entry.getValue());
				}
			}
		}
		return result;
	}

}
