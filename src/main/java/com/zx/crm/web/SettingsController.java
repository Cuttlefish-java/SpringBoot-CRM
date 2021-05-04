package com.zx.crm.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings")
public class SettingsController {
    @RequestMapping("/index")
    public String settingsIndex(){
        return "settings/index";
    }

    @RequestMapping("/dept/index")
    public String settingsDeptIndex(){
        return "settings/dept/index";
    }
    @RequestMapping("/qx/index")
    public String settingsQxIndex(){
        return "settings/qx/index";
    }
    @RequestMapping("/qx/user/detail")
    public String settingsQxUserDetail(){
        return "settings/qx/user/detail";
    }
    @RequestMapping("/qx/user/index")
    public String settingsQxUserIndex(){
        return "settings/qx/user/index";
    }
}
