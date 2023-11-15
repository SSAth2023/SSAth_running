package com.ssath.map.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssath.map.model.dao.UserDao;
import com.ssath.map.model.dto.User;

@Service
public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User selectUser(String userId) {
		return userDao.selectUser(userId);
	}

	@Override
	public List<User> selectUsers() {
		return userDao.selectUsers();
	}

	@Override
	public int createUser(User user) {
		return userDao.createUser(user);
	}

	@Override
	public int deleteUser(int userId) {
		return userDao.deleteUser(userId);
	}

	@Override
	public String LoginUser(String userId, String password) {
		return userDao.loginUser(userId, password);
	}

	@Override
	public boolean logoutUser() {
		return userDao.logoutUser();
	}

}
