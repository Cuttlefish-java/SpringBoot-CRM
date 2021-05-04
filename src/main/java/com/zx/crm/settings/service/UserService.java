package com.zx.crm.settings.service;

import com.zx.crm.exception.LoginException;
import com.zx.crm.exception.UpdateUserPwdException;
import com.zx.crm.settings.model.User;
import javafx.fxml.LoadException;

import java.util.List;

public interface UserService {
    User login(String logAct, String logPwd, String ip) throws LoginException;

    int updateUserPwd(String oldPwd, String confirmPwd, String newPwd, User user) throws UpdateUserPwdException;

    List<User> getUserList();
}
