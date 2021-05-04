package com.zx.crm.settings.mapper;

import com.zx.crm.settings.model.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectUserByActAndPwd(Map<String, String> userMap);

    int updateUserPwd(String id, String newPwd);

    List<User> getUserList();
}