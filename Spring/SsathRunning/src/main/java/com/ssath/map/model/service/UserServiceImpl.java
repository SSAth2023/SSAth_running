package com.ssath.map.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssath.map.model.dao.UserDao;
import com.ssath.map.model.dto.User;

@Service
public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	
	@Autowired
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
	public int deleteUser(String userId) {
		return userDao.deleteUser(userId);
	}

	@Override
	public String loginUser(String userId, String password) {
		User tmp = userDao.selectUser(userId);
		if(tmp != null&&tmp.getPassword().equals(password)) {
			return userDao.loginUser(userId, password);
		}
		return null;
	}

	@Override
	public boolean logoutUser() {
		return userDao.logoutUser();
	}

}
