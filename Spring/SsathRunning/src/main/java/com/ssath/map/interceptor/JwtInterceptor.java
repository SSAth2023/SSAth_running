package com.ssath.map.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssath.map.util.JwtUtil;

@Component
public class JwtInterceptor implements HandlerInterceptor{
	private static final String HEADER_AUTH = "access-token";
	
	private JwtUtil jwtUtil;
	
	@Autowired
	public void setJwtUtil(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//get방식 중 미리 던저보는 요청들은 true
		if(request.getMethod().equals("OPTIONS"))
			return true;
		
		String token = request.getHeader(HEADER_AUTH);
		
		if(token != null) {
			jwtUtil.valid(token);
			return true;
		}
		
		throw new Exception("유효하지 않은 접근입니다.");
		
	}
}