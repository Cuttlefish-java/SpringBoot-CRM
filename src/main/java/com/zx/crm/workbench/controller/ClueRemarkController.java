package com.zx.crm.workbench.controller;

import com.zx.crm.settings.model.User;
import com.zx.crm.util.DateTimeUtil;
import com.zx.crm.util.UUIDUtil;
import com.zx.crm.workbench.model.Activity;
import com.zx.crm.workbench.model.ClueRemark;
import com.zx.crm.workbench.service.ClueRemarkService;
import com.zx.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/workbench/clueRemark")
public class ClueRemarkController {
    @Autowired
    private ClueRemarkService clueRemarkService;
    @Autowired
    private ClueService clueService;

    @ResponseBody
    @RequestMapping("deleteActivityById")
    public boolean deleteActivityById(String id){
        boolean flag = clueRemarkService.deleteActivityById(id);
        return  flag;
    }
    @ResponseBody
    @RequestMapping("getActivityListByClueId")
    public List<Activity> getActivityListByClueId(String clueId){
        List<Activity> activities = clueRemarkService.getActivityListByClueId(clueId);
        return  activities;
    }
    @ResponseBody
    @RequestMapping("getClueRemarkByClueId")
    public List<ClueRemark> getClueRemarkByClueId(String clueId){
        List<ClueRemark> clueRemarks = clueRemarkService.getClueRemarks(clueId);
        return  clueRemarks;
    }
    @ResponseBody
    @RequestMapping("getClueRemarkById")
    public ClueRemark getClueRemarkById(String id){
        ClueRemark clueRemark =  clueRemarkService.getClueRemarkById(id);
        return  clueRemark;
    }
    @ResponseBody
    @PostMapping("saveRemark")
    public boolean saveRemark(HttpServletRequest request,ClueRemark clueRemark){
        clueRemark.setId(UUIDUtil.getUUID());
        User user = (User) request.getSession().getAttribute("user");
        clueRemark.setCreateBy(user.getName());
        clueRemark.setCreateTime(DateTimeUtil.getSysTime());
        clueRemark.setEditFlag("0");
        boolean flag = clueRemarkService.saveClueRemark(clueRemark);
        return flag;
    }

    @ResponseBody
    @PostMapping("updateClueRemark")
    public boolean updateClueRemark(HttpServletRequest request,ClueRemark clueRemark){
        User user = (User) request.getSession().getAttribute("user");
        clueRemark.setEditBy(user.getName());
        clueRemark.setEditTime(DateTimeUtil.getSysTime());
        clueRemark.setEditFlag("1");

        boolean flag = clueRemarkService.updateClueRemark(clueRemark);
        return flag;
    }

    @ResponseBody
    @GetMapping("deleteClueRemark")
    public boolean deleteClueRemark(String id){

        boolean flag = clueRemarkService.deleteClueRemark(id);
        return flag;
    }
}
