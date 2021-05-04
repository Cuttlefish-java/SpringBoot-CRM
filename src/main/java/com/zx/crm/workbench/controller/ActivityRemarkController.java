package com.zx.crm.workbench.controller;

import com.zx.crm.settings.model.User;
import com.zx.crm.util.DateTimeUtil;
import com.zx.crm.util.UUIDUtil;
import com.zx.crm.workbench.model.ActivityRemark;
import com.zx.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/activityRemark")
public class ActivityRemarkController {
    @Autowired
    private ActivityRemarkService activityRemarkService;
    @GetMapping("/getActivityRemarkByActivityId")
    @ResponseBody
    public List<ActivityRemark> getActivityRemarkByActivityId(String activityId){
        System.out.println("getActivityRemarkByActivityId===="+activityId);
        List<ActivityRemark> activityRemarkList = activityRemarkService.getActivityRemarkByActivityId(activityId);
        return activityRemarkList;
    }
    @GetMapping("deleteActivityRemark")
    @ResponseBody
    public boolean deleteActivityRemark(String id){
        System.out.println("deleteActivityRemark===="+id);
        boolean flag = activityRemarkService.deleteActivityRemark(id);
        return flag;
    }

    @GetMapping("saveActivityRemarkBtn")
    @ResponseBody
    public Map<String,Object> saveActivityRemarkBtn(HttpServletRequest request,ActivityRemark activityRemark){
        activityRemark.setId(UUIDUtil.getUUID());
        User user = (User) request.getSession().getAttribute("user");
        activityRemark.setCreateBy(user.getName());
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        activityRemark.setEditFlag("0");
        System.out.println(activityRemark+"................");
        Map<String, Object> map = new  HashMap();
        ActivityRemark activityRemark1 = activityRemarkService.saveActivityRemarkBtn(activityRemark);
        if (activityRemark1!=null){
            map.put("success",true);
            map.put("activityRemark",activityRemark1);
            return map;
        }else {
            map.put("success",false);
            return map;
        }

    }
    @GetMapping("/getActivityRemarkById")
    @ResponseBody
    public Map<String,Object> getActivityRemarkById(String id){
        Map<String,Object> map = new HashMap<>();
        ActivityRemark activityRemark =activityRemarkService.selectActivityRemarkById(id);
        if (activityRemark!=null){
            map.put("success",true);
            map.put("activityRemark",activityRemark);
            return map;
        }else {
            map.put("success",false);
            return map;
        }
    }
    @GetMapping("/updateActivityRemark")
    @ResponseBody
    public Map<String,Object> updateActivityRemark(HttpServletRequest request ,ActivityRemark activityRemark){
        Map<String,Object> map = new HashMap<>();
        User user = (User) request.getSession().getAttribute("user");
        activityRemark.setEditBy(user.getName());
        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        System.out.println(activityRemark);
        ActivityRemark remark = activityRemarkService.updateActivityRemark(activityRemark);
        if (remark!=null){
            map.put("activityRemark",remark);
            map.put("success",true);
        }else {
            map.put("success",false);
        }
        return map;
    }
}
