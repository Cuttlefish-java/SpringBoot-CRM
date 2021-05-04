package com.zx.crm.config;

import com.zx.crm.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    //定义拦截器，作用是为了防止用户在不登录的状态下访问系统
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //定义需要拦截的路径
        String[] addPathPatterns = {
                "/**"
        };
        //定义不需要拦截的路径
        String[] excludePathPatterns = {
                "/index",
                "/image/**",
                "/jquery/**",
                "/css/**",
                "/settings/user/**",

        };
        registry.addInterceptor(new LoginInterceptor()) //添加要注册的拦截 器对象
                .addPathPatterns(addPathPatterns) //添加需要拦截的路径
                .excludePathPatterns(excludePathPatterns); //添加不需要拦截的路径
    }
}
