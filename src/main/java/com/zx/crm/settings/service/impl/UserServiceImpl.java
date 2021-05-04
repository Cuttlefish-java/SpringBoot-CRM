package com.zx.crm.settings.service.impl;

import com.zx.crm.exception.LoginException;
import com.zx.crm.exception.UpdateUserPwdException;
import com.zx.crm.settings.mapper.UserMapper;
import com.zx.crm.settings.model.User;
import com.zx.crm.settings.service.UserService;
import com.zx.crm.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(String logAct, String logPwd, String ip) throws LoginException {
        Map<String,String> userMap = new HashMap<>();
        userMap.put("logAct",logAct);
        userMap.put("logPwd",logPwd);
        User user = userMapper.selectUserByActAndPwd(userMap);
        System.out.println(user);
        if (user == null) {
            throw new LoginException("账号密码错误");
        }
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if(expireTime.compareTo(currentTime)<0){

            throw new LoginException("账号已失效");

        }
        String lockState = user.getLockState();
        if("0".equals(lockState)){

            throw new LoginException("账号已锁定");

        }
        String allowIPS = user.getAllowIPS();
        System.out.println(ip);
        if(!allowIPS.contains(ip)){

            throw new LoginException("ip不在允许的范围内");

        }
        return user;
    }

    @Override
    public List<User> getUserList() {
        return userMapper.getUserList();
    }

    @Transactional
    @Override
    public int updateUserPwd(String oldPwd, String confirmPwd, String newPwd, User user) throws UpdateUserPwdException {
        System.out.println(oldPwd);
        System.out.println(newPwd);
        System.out.println(confirmPwd);
        if (!user.getLoginPwd().equals(oldPwd)){
            throw new UpdateUserPwdException("原密码输入不正确");
        }
        if (!newPwd.equals(confirmPwd)){
            throw new UpdateUserPwdException("两次输入的密码不一致");
        }
        if (oldPwd.equals(newPwd)&&oldPwd.equals(confirmPwd)){
            throw new UpdateUserPwdException("新密码不可以和旧密码一致");
        }
        user.setLoginPwd(newPwd);
        int result =  userMapper.updateByPrimaryKeySelective(user);
        if (result<0){
            throw new UpdateUserPwdException("密码更新失败");
        }
        return result;
    }
}
