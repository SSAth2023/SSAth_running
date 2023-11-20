package com.ssath.map.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssath.map.model.dto.User;
import com.ssath.map.model.service.UserService;
import com.ssath.map.util.JwtUtil;

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(tags="유저 컨트롤러")
@RequestMapping("/api")
public class UserRestController {
	
	private final String SUCCESS = "SUCCESS";
	private final String FAIL = "FAIL";
	
	private JwtUtil util;
	private UserService userService;

	@Autowired
	public void setJwtUtil(JwtUtil util) {
		this.util = util;
	}
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService=userService;
	}
	
	//특정 유저 정보 출력
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> detail(@PathVariable String userId){
		User user = userService.selectUser(userId);
		if(user == null)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	//모든 유저 정보 출력
	@GetMapping("/user/list")
	public ResponseEntity<?> list(){
		List<User> list = userService.selectUsers();
		if(list == null || list.size()==0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
	
	//회원 가입
	@PostMapping("/user/signup")
	public ResponseEntity<String> signup(@RequestBody User user){
		int result = userService.createUser(user);
		if(result == 0)
			return new ResponseEntity<String>(FAIL,HttpStatus.NO_CONTENT);
		return new ResponseEntity<String>(SUCCESS,HttpStatus.OK);
	}
	
	//회원 탈퇴
	@DeleteMapping("/user/delete/{userId}")
	public ResponseEntity<String> delete(@PathVariable String userId){
		int result = userService.deleteUser(userId);
		if(result == 0)
			return new ResponseEntity<String>(FAIL,HttpStatus.NO_CONTENT);
		return new ResponseEntity<String>(SUCCESS,HttpStatus.OK);
	}
	
	//로그인
	@PostMapping("/user/login")
	public ResponseEntity<Map<String,Object>> login(@RequestBody User user){
		Map<String,Object> result = new HashMap<String, Object>();
		String name = userService.loginUser(user.getUserId(), user.getPassword());
		System.out.println(name);
		if(name == null) {
			result.put("access-token", null);
			result.put("message", "FAIL");
			return new ResponseEntity<Map<String,Object>>(result,HttpStatus.NO_CONTENT);
		}
		result.put("access-token", util.createToken("name", name));
		result.put("message", "SUCCESS");
		return new ResponseEntity<Map<String,Object>>(result,HttpStatus.ACCEPTED);
		
	}
	
	//로그아웃
	@GetMapping("/user/logout")
	public ResponseEntity<Boolean> logout(@ApiIgnore HttpSession session){
		session.invalidate();
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
}
