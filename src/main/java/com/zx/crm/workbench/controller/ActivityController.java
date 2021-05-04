package com.zx.crm.workbench.controller;

import com.zx.crm.exception.SaveActivityException;
import com.zx.crm.settings.model.User;
import com.zx.crm.settings.service.UserService;
import com.zx.crm.util.DateTimeUtil;
import com.zx.crm.util.UUIDUtil;
import com.zx.crm.workbench.model.Activity;
import com.zx.crm.workbench.model.PageList;
import com.zx.crm.vo.PaginationVO;
import com.zx.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;


    @PostMapping("saveActivity")
    @ResponseBody
    public String saveActivity(Activity activity, HttpServletRequest request) {
        System.out.println("saveActivity()");
        System.out.println(activity);
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        activity.setId(id);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);
        int result = 0;
        try {
            result = activityService.saveActivity(activity);
            System.out.println(result);
        } catch (SaveActivityException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "添加成功";
    }

    @GetMapping("pageList")
    @ResponseBody
    public PaginationVO pageList(PageList pageList) {
        System.out.println(pageList);
        PaginationVO paginationVO = activityService.selectiveActivity(pageList);
        System.out.println(paginationVO);
        return paginationVO;
    }
    @GetMapping("deleteActivity")
    @ResponseBody
    public boolean deleteActivity(String [] id, Model model) {
        boolean flag = false;
        for (int i = 0;i<id.length;i++){
            String substring = id[i].substring(id[i].length() - 1, id[i].length());
            if ("/".equals(substring)&&id[i].contains(substring)){
                id[i] = id[i].substring(0,id[i].length()-1);
                System.out.println(id[i]);
            }
        }
        flag = activityService.deleteActivity(id);
        return flag;
    }

    @PostMapping("getActivityAndUserListByActivityId")
    @ResponseBody
    public Map<String ,Object> getActivityAndUserListByActivityId(String id) {
        String string = id.substring(id.length()-1,id.length());
        if ("/".equals(string)&&id.contains(string)){
            id = id.substring(0,id.length()-1);
        }
        Map<String,Object> map = activityService.getActivityAndUserListByActivityId(id);
        return map;
    }
    @GetMapping("updateActivity")
    @ResponseBody
    public boolean updateActivity(HttpServletRequest request,Activity activity) {
        boolean flag = false;
        String string = activity.getId().substring(activity.getId().length()-1,activity.getId().length());
        if ("/".equals(string)&&activity.getId().contains(string)){
            activity.setId(activity.getId().substring(0,activity.getId().length()-1));
        }
        String sysTime = DateTimeUtil.getSysTime();
        activity.setEdittime(sysTime);
        User user= (User) request.getSession().getAttribute("user");
        String editBy = user.getName();
        activity.setEditby(editBy);
        System.out.println(activity);
        flag = activityService.updateActivity(activity);
        return flag;
    }
    @RequestMapping("/detail")
    public String workbenchActivityDetail(String id,Model model){
        System.out.println(id);
        Activity activity = activityService.detail(id);
        model.addAttribute("activity",activity);
        return "workbench/activity/detail";
    }
}
