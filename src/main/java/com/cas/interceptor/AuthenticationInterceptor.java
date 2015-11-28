package com.cas.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cas.model.User;

public class AuthenticationInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/* System.out.println("In interceptor pre Handle"); */
		if (!request.getRequestURI().equals("/ConfigAsService/")
				&& !request.getRequestURI().equals("/ConfigAsService/performlogin")
				&& !request.getRequestURI().equals("/ConfigAsService/register")
				&& !request.getRequestURI().equals("/ConfigAsService/performregister")) {
			User user = (User) request.getSession().getAttribute("LOGGEDIN_USER");
			if (user == null) {
				response.sendRedirect("/ConfigAsService/");
				return false;
			}
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
