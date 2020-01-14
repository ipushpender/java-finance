//package com.jungleegames.finance;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//@Component
//public class SessionInterceptor extends HandlerInterceptorAdapter {
//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		if (request.getRequestURI().contains("login"))
//			return true;
//
//		if (request.getSession().toString()!=null)
//		{
//			return true;
//		} else {
//			response.sendRedirect(request.getContextPath() + "/login");
//			return false;
//		}
//	}
//
//}
