package com.zx.crm.settings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/settings/dictionary")
public class DictionaryController {
    @RequestMapping("/index")
    public String settingsDictionaryIndex(){
        return "settings/dictionary/index";
    }
    @RequestMapping("/type/index")
    public String settingsDictionaryTypeIndex(){
        return "settings/dictionary/type/index";
    }
    @RequestMapping("/value/index")
    public String settingsDictionaryValueIndex(){
        return "settings/dictionary/value/index";
    }
    @RequestMapping("/type/save")
    public String settingsDictionaryTypeSave(){
        return "settings/dictionary/type/save";
    }
    @RequestMapping("/type/edit")
    public String settingsDictionaryTypeEdit(){
        return "settings/dictionary/type/edit";
    }
    @RequestMapping("/value/save")
    public String settingsDictionaryValueSave(){
        return "settings/dictionary/value/save";
    }
    @RequestMapping("/value/edit")
    public String settingsDictionaryValueEdit(){
        return "settings/dictionary/value/edit";
    }
}
