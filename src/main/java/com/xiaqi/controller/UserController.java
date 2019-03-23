package com.xiaqi.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaqi.annotation.MyAutowired;
import com.xiaqi.annotation.MyController;
import com.xiaqi.annotation.MyRequestMapping;
import com.xiaqi.annotation.MyRequestParam;
import com.xiaqi.service.UserService;

/**
 * user controller
 * @author xiaqi
 */
@MyController
@MyRequestMapping("/user")
public class UserController {
	
	@MyAutowired
	private UserService service;

	@MyRequestMapping("/login")
	public void login(HttpServletRequest request,HttpServletResponse response,
						@MyRequestParam("username") String username,@MyRequestParam("password") String password) throws IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		if (service.login(username, password)) {
			response.getWriter().write("你好！ "+username);
		} else {
			response.getWriter().write("密码错误"+username);
		}
	}
	
}
