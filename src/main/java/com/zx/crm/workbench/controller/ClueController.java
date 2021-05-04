package com.zx.crm.workbench.controller;

import com.zx.crm.settings.service.UserService;
import com.zx.crm.workbench.model.Activity;
import com.zx.crm.workbench.model.Clue;
import com.zx.crm.settings.model.User;
import com.zx.crm.util.DateTimeUtil;
import com.zx.crm.util.UUIDUtil;
import com.zx.crm.vo.PaginationVO;
import com.zx.crm.workbench.model.ClueActivityRelation;
import com.zx.crm.workbench.model.Tran;
import com.zx.crm.workbench.service.ActivityService;
import com.zx.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {
    @Autowired
    private ClueService clueService ;
    @Autowired
    private UserService userService ;
    @Autowired
    private ActivityService activityService ;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @RequestMapping("/index")
    public String workbenchClueIndex(Model model){
        Object appellation =redisTemplate.opsForValue().get("appellation");
        Object clueState = redisTemplate.opsForValue().get("clueState");
        Object source = redisTemplate.opsForValue().get("source");
        System.out.println(redisTemplate+"reds");
        System.out.println(appellation.getClass());
        System.out.println(clueState.getClass());
        System.out.println(source.getClass());
        model.addAttribute("appellationList",appellation);
        model.addAttribute("clueStateList",clueState);
        model.addAttribute("sourceList",source);
        System.out.println(model);
        return "workbench/clue/index";
    }
    @RequestMapping("/saveClue")
    @ResponseBody
    public boolean saveClue(Clue clue, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        clue.setCreateBy(user.getName());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        clue.setId(UUIDUtil.getUUID());
        System.out.println(clue);
        boolean flag = clueService.saveClue(clue);
        return flag;
    }
    @RequestMapping("/pageList")
    @ResponseBody
    public PaginationVO<Clue> pageList(String pageNo,String pageSize,Clue clue){
        System.out.println(clue);
        System.out.println(pageNo);
        System.out.println(pageSize);
        PaginationVO<Clue> paginationVO=clueService.selectiveClue(pageNo,pageSize,clue);
        System.out.println(paginationVO);
        return paginationVO;
    }

    @RequestMapping("/getClueByIdAndGetUserList")
    @ResponseBody
    public Map<String,Object> getClueByIdAndGetUserList(String id){
        System.out.println(id);
        Map<String,Object> map = new HashMap<>();
        Clue clue = clueService.getClueById(id);
        List<User> users = userService.getUserList();
        map.put("clue",clue);
        map.put("users",users);
        return map;
    }

    @RequestMapping("/updateClue")
    @ResponseBody
    public boolean updateClue(HttpServletRequest request,Clue clue){
        User user = (User) request.getSession().getAttribute("user");
        clue.setEditBy(user.getName());
        clue.setEditTime(DateTimeUtil.getSysTime());
        System.out.println(clue);
        boolean flag = clueService.updateClue(clue);
        return flag;
    }

    @RequestMapping("/getActivityListByName")
    @ResponseBody
    public List<Activity> getActivityListByName(String name){
        List<Activity> activities = activityService.getActivityListByName(name);
        return activities;
    }
    @RequestMapping("/deleteClueById")
    @ResponseBody
    public boolean deleteClueById(String[] id){
        boolean flag = clueService.deleteClue(id);
        return flag;
    }

    @RequestMapping("/detail")
    public String workbenchClueDetail(String id,Model model){
        Object appellation =redisTemplate.opsForValue().get("appellation");
        Object clueState = redisTemplate.opsForValue().get("clueState");
        Object source = redisTemplate.opsForValue().get("source");
        System.out.println(redisTemplate+"reds");
        System.out.println(appellation.getClass());
        System.out.println(clueState.getClass());
        System.out.println(source.getClass());
        System.out.println(id);
        Clue clue = clueService.getClue(id);
        model.addAttribute("clue",clue);
        model.addAttribute("appellationList",appellation);
        model.addAttribute("clueStateList",clueState);
        model.addAttribute("sourceList",source);
        return "workbench/clue/detail";
    }
    @RequestMapping("/getActivityByNotHaveClueId")
    @ResponseBody
    public List<Activity> getActivityByNotHaveClueId(String clueId,String name){
        System.out.println("getActivityByNotHaveClueId------"+clueId);
        Map<String,String> map = new HashMap<>();
        map.put("clueId",clueId);
        map.put("name",name);
        List<Activity> activities = activityService.getActivityByNotHaveClueId(map);
        return activities;
    }
    @RequestMapping("/convert")
    public String workbenchClueConvert(Clue clue, Model model){
        Object stageList =redisTemplate.opsForValue().get("stage");
        System.out.println(clue);
        model.addAttribute("clue",clue);
        model.addAttribute("stageList",stageList);
        return "workbench/clue/convert";
    }
    @RequestMapping("/saveActivityBindClue")
    @ResponseBody
    public boolean saveActivityBindClue(ClueActivityRelation clueActivityRelation){
        System.out.println(clueActivityRelation);
        boolean flag = clueService.saveActivityBindClue(clueActivityRelation);
        return flag;
    }
    @RequestMapping("/clueConversion")
    public String clueConversion(String clueId, Tran tran,String flag,HttpServletRequest request){
        System.out.println(tran);
        System.out.println(clueId+"-----------");
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        System.out.println(flag);
        boolean result = false;
        if (flag!=null){
            tran.setId(UUIDUtil.getUUID());
            tran.setCreateBy(createBy);
            tran.setCreateTime(DateTimeUtil.getSysTime());
        }
        result = clueService.clueConversion(clueId,tran,createBy);
        //System.out.println("result==========="+result);
        if (result) {
            return "workbench/clue/index";
        }
        return "workbench/clue/index";
    }
}
