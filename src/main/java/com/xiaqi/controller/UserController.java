package com.xiaqi.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaqi.annotation.MyAutowired;
import com.xiaqi.annotation.MyController;
import com.xiaqi.annotation.MyRequestMapping;
import com.xiaqi.annotation.MyRequestParam;
import com.xiaqi.annotation.MyResponseBody;
import com.xiaqi.entity.User;
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

	@MyResponseBody
	@MyRequestMapping("/login")
	public String login(@MyRequestParam("username") String username,
					    @MyRequestParam("password") String password) throws IOException {
		if (service.login(username, password)) {
			return "你好！"+username;
		}
		return "用户名或密码错误！";
	}
	
	@MyResponseBody
	@MyRequestMapping("/getUserById")
	public User getUserById(@MyRequestParam("userId") int userId) {
		return service.getUserById(userId);
	}
	
	
}
