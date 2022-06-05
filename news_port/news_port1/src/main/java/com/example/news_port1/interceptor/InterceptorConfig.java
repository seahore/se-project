package com.example.news_port1.interceptor;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns拦截的路径
        String[] addPathPatterns = {
   "/*"
        };
        //excludePathPatterns排除的路径
        String[] excludePathPatterns = {
                "/login","/register","/hotSearch","/searchPassage","/selectLatestPassage","/ad/*","/selectPassage"
        };
        //创建用户拦截器对象并指定其拦截的路径和排除的路径
        registry.addInterceptor(new UserInterceptor()).addPathPatterns(addPathPatterns).excludePathPatterns(excludePathPatterns);
    }
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//配置映射
                .allowedOriginPatterns("*")//设置允许那些域来访问
                .allowedHeaders("*")//请求头字段
                .allowedMethods("*")//请求方式(GET,POST,DELETE,PUT)
                .allowCredentials(true)//是否允许携带cookie
                .maxAge(360000);//设置3600秒，表示3600秒之内浏览器不必再次询问

    }
}
