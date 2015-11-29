package com.cas.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cas.model.User;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (!("/ConfigAsService/".equals(request.getRequestURI()))
                && !("/ConfigAsService/performlogin".equals(request.getRequestURI()))
                && !("/ConfigAsService/register".equals(request.getRequestURI()))
                && !("/ConfigAsService/performregister").equals(request.getRequestURI())) {

            User user = (User) request.getSession().getAttribute("LOGGEDIN_USER");
            if (user == null) {
                response.sendRedirect("/ConfigAsService/");
                return false;
            }
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        //Auto-generated
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //Auto-generated
    }

}
