package com.ssath.map.model.dto;

public class User {
	
	private String userId;
	private String name;
	private String password;
	private int age;
	
	public User() {
	}

	public User(String userId, String name, String password, int age) {
		super();
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.age = age;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", password=" + password + ", age=" + age + "]";
	}
	
}
