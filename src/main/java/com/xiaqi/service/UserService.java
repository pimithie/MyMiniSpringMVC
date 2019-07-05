package com.xiaqi.service;

import com.xiaqi.entity.User;

/**
 * @author xiaqi
 */
public interface UserService {
	
	/**
	 * 	login service method 登陆服务方法
	 */
	public boolean login (String username,String password);
	
	/**
	 * according to the id to find the user
	 * @param userId
	 * @return
	 */
	User getUserById(int userId)
	
}
