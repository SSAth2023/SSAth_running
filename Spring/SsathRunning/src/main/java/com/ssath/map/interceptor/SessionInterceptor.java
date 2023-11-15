package com.ssath.map.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		// 세션에 로그인 정보가 있다면 그대로 진행한다.
		if(session.getAttribute("loginUser")!=null) {
			return true;
		}
		// 세션 정보가 없다면 index로 redirect 시킨다.
		else {
			response.sendRedirect(request.getContextPath()+"/index");
			return false;
		}
	}
}