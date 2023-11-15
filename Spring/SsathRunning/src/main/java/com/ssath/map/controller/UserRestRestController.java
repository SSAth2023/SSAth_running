package com.ssath.map.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssath.map.model.service.UserService;

import io.swagger.annotations.Api;

@RestController
@Api(tags="유저 컨트롤러")
@RequestMapping("/api")
public class UserRestRestController {
	
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService=userService;
	}
	
}
