package com.zx.crm.interceptor;

import com.zx.crm.settings.model.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("--------编写登录拦截规则-------");
        //编写拦截规则
        //true：通过
        //false：不通过
        //从 session 中获取结果
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            response.sendRedirect(request.getContextPath() + "/index");
            return false;
        }
        //如果有user信息，则放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
