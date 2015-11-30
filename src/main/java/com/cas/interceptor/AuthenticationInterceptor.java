package com.cas.interceptor;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cas.model.User;
import com.cas.service.impl.FileServiceImpl;

public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = Logger.getLogger(FileServiceImpl.class.getName());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (!"/ConfigAsService/".equals(request.getRequestURI())
                && !"/ConfigAsService/register".equals(request.getRequestURI())
                && !"/ConfigAsService/performregister".equals(request.getRequestURI())
                && !"/".equals(request.getRequestURI()) && !"/register".equals(request.getRequestURI())
                && !"/ConfigAsService/performlogin".equals(request.getRequestURI())) {

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
        LOGGER.log(Level.ALL, "In post handle method");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        LOGGER.log(Level.ALL, "In after complete method");
    }

}
