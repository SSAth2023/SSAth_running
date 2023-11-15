package com.ssath.map.model.service;

import java.util.List;

import com.ssath.map.model.dto.User;

public interface UserService {

	public User selectUser(String userId);
	
	public List<User> selectUsers();
	
	public int createUser(User user);
	
	public int deleteUser(int userId);
	
	public String LoginUser(String userId, String password);
	
	public boolean logoutUser();

}
