package com.example.news_port1.interceptor;

import com.example.news_port1.model.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "360000");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept");
        //从session中获取user的信息
        Object userIdObject=request.getSession().getAttribute("userId");
        //判断用户是否登录
        if (null==userIdObject){
            //没有登录重定向到登录页面
            response.sendRedirect(request.getContextPath()+"/index");
            return false;
        }
        //已登录，放行
        return true;
    }
}
