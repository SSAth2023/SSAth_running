package com.ssath.map.model.dao;

import java.util.List;

import com.ssath.map.model.dto.User;

public interface UserDao {
	
	//특정 유저 정보 출력
	public User selectUser(String userId);
	
	//유저들의 정보 출력
	public List<User> selectUsers();
	
	//회원 가입
	public int createUser(User user);
	
	//회원 탈퇴
	public int deleteUser(int userId);
	
	//로그인
	public String loginUser(String userId, String password);
	
	//로그아웃
	public boolean logoutUser();
}
