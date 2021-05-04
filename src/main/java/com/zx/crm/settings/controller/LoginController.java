package com.zx.crm.settings.controller;

import com.zx.crm.exception.LoginException;
import com.zx.crm.exception.UpdateUserPwdException;
import com.zx.crm.settings.model.User;
import com.zx.crm.settings.service.UserService;
import com.zx.crm.util.IP;
import com.zx.crm.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/settings/user")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public Object login(String logAct, String logPwd,
                      HttpServletRequest request) {
        User user = null;
        logPwd = MD5Util.getMD5(logPwd);
        String ip = IP.getIpAddress(request);
        try {
            user = userService.login(logAct, logPwd, ip);
            request.getSession().setAttribute("user",user);
        } catch (LoginException e) {
            e.printStackTrace();
           return e.getMessage();
        }
        return user;
    }
    @GetMapping("/exit")
    public String login(HttpServletRequest request) {
        System.out.println("33333");
        request.getSession().invalidate();
        return "redirect:/login";
    }
    @PostMapping("/updateUserPwd")
    @ResponseBody
    public String updateUserPwd(String oldPwd ,String newPwd,String confirmPwd,HttpServletRequest request) {

        System.out.println("confirmPwd");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        oldPwd = MD5Util.getMD5(oldPwd);
        newPwd = MD5Util.getMD5(newPwd);
        confirmPwd = MD5Util.getMD5(confirmPwd);
        int result = 0;
        try {
            result = userService.updateUserPwd(oldPwd,confirmPwd,newPwd,user);
            return "密码修改成功，请重新登录";
        } catch (UpdateUserPwdException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
    @RequestMapping("/getUserList")
    @ResponseBody
    public Map<String,Object> getUserList(){
        Map<String,Object> map = new HashMap<>();
        List<User> users = userService.getUserList();
        map.put("users",users);
        return map;
    }
}
