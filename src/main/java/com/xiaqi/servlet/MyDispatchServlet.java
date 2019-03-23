package com.xiaqi.servlet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaqi.annotation.MyAutowired;
import com.xiaqi.annotation.MyController;
import com.xiaqi.annotation.MyRequestMapping;
import com.xiaqi.annotation.MyService;

/**
 * my dispatch servlet
 * 
 * @author xiaqi
 */
public class MyDispatchServlet extends HttpServlet {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * save all component class names 保存所有的组件
	 */
	private final Map<String, Object> componentMapping = new HashMap<String, Object>();

	/**
	 * save all component Class object 保存所有组件Class对象
	 */
	private final List<Class<?>> allComponentsClass = new ArrayList<Class<?>>();

	/**
	 * save all handler mapping
	 */
	private final Map<String, Method> handlerMapping = new HashMap<String, Method>();

	@Override
	public void init() throws ServletException {
		// scanning basic package to search for the component
		// 扫描basic package,搜寻组件
		// -----------先写死，等会换配置文件
		scanBasicPackage("com.xiaqi");
		try {
			// instantiation component 实例化组件
			instantiationComponent();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		// dependency injection 依赖注入
		dependencyInjection();

		// initialize HandlerMapping 初始化HandlerMapping
		initializeHandlerMapping();

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	/**
	 * scanning the basic package for component
	 * 
	 * @author xiaqi 包扫描
	 */
	private void scanBasicPackage(String basicPackage) {
		// com.xiaqi
		URL url = this.getClass().getClassLoader().getResource(basicPackage.replace(".", "/"));
		// retrieve the package's file path 取得对应的文件路径
		String rootPath = url.getFile();
		logger.info("basicPackagePath:" + rootPath);
		File rootDirectory = new File(rootPath);
		// list all files 列出根目录所有文件
		String[] filesStr = rootDirectory.list();
		// traverse all files 遍历
		for (String fileStr : filesStr) {
			File file = new File(rootDirectory, fileStr);
			// if directory,recursion 为目录，递归
			if (file.isDirectory()) {
				scanBasicPackage(basicPackage + "." + fileStr);
			} else {
				// otherwise 为文件
				String className = (basicPackage + "." + fileStr).replace(".class", "");
				Class<?> clazz = null;
				try {
					clazz = Class.forName(className);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				if (null != clazz) {
					// present MyController annotation 有MyController注解
					if (clazz.isAnnotationPresent(MyController.class) || clazz.isAnnotationPresent(MyService.class)) {
						allComponentsClass.add(clazz);
						logger.info("find a component:" + className);
					}
				}
			}
		}
	}

	/**
	 * instantiation component 实例化组件
	 * 
	 * @author xiaqi
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	private void instantiationComponent() throws InstantiationException, IllegalAccessException {
		// instantiation all component
		for (Class<?> clazz : allComponentsClass) {
			// first lowercase 将类名第一个字母转小写
			char[] chs = clazz.getSimpleName().toCharArray();
			chs[0] = Character.toLowerCase(chs[0]);
			componentMapping.put(new String(chs), clazz.newInstance());
			logger.info("instantiation component:" + clazz.getName());
		}
	}

	/**
	 * initialize HandlerMapping 初始化HandlerMapping
	 * 
	 * @author xiaqi
	 */
	private void initializeHandlerMapping() {
		// traverse all controller component to search for the handler
		// 搜寻所有的controller中处理器
		for (Map.Entry<String, Object> entry : componentMapping.entrySet()) {
			Class<? extends Object> clazz = entry.getValue().getClass();
			if (clazz.isAnnotationPresent(MyController.class)) {
				String classPath = "";
				if (clazz.isAnnotationPresent(MyRequestMapping.class)) {
					classPath = clazz.getAnnotation(MyRequestMapping.class).value();
				}
				// traverse all method 遍历所有的方法对象
				Method[] methods = clazz.getDeclaredMethods();
				for (Method method : methods) {
					if (method.isAnnotationPresent(MyRequestMapping.class)) {
						String methodPath = method.getAnnotation(MyRequestMapping.class).value();
						handlerMapping.put(classPath + methodPath, method);
						logger.info("handler mapping is put:" + (classPath + methodPath) + "--->" + method);
					}
				}
			}
		}
	}

	/**
	 * finishing dependency injection 完成依赖注入
	 * 
	 * @author xiaqi
	 */
	public void dependencyInjection() {
		for (Map.Entry<String, Object> entry : componentMapping.entrySet()) {
			Object instance = entry.getValue();
			Class<? extends Object> clazz = instance.getClass();
			// traverse all field to autowire 搜寻需要进行自动装配的字段
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (field.isAnnotationPresent(MyAutowired.class)) {
					// search for the suitable type 搜寻满足的类型
					for (Object component : componentMapping.values()) {
						if (field.getType().isAssignableFrom(component.getClass())) {
							field.setAccessible(true);
							try {
								field.set(instance, component);
								logger.info("dependency injection:"+field+"--->"+component);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
}
