package com.xiaqi.service.impl;

import com.xiaqi.annotation.MyService;
import com.xiaqi.service.UserService;

@MyService
public class UserServiceImpl implements UserService{
	
	@Override
	public boolean login(String username, String password) {
		// 模拟dao层，从数据库搜索到的数据
		return "xiaqi".equals(username) && "123456".equals(password);
	}

}
