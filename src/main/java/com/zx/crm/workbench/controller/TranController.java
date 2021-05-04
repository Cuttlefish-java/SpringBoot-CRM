package com.zx.crm.workbench.controller;

import com.zx.crm.settings.model.DicValue;
import com.zx.crm.settings.model.User;
import com.zx.crm.util.DateTimeUtil;
import com.zx.crm.util.UUIDUtil;
import com.zx.crm.vo.PaginationVO;
import com.zx.crm.workbench.model.Activity;
import com.zx.crm.workbench.model.Contacts;
import com.zx.crm.workbench.model.Tran;
import com.zx.crm.workbench.model.TranHistory;
import com.zx.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/workbench/transaction")
public class TranController {
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Autowired
    private TranService tranService;

    @RequestMapping("/index")
    public String workbenchTransactionIndex(Model model){
        Object stage =redisTemplate.opsForValue().get("stage");
        Object source = redisTemplate.opsForValue().get("source");
        Object transactionType = redisTemplate.opsForValue().get("transactionType");

        //Tran tran = clueService.getClue(id);
        model.addAttribute("stageList",stage);
        model.addAttribute("sourceList",source);
        model.addAttribute("transactionTypeList",transactionType);
        return "workbench/transaction/index";
    }
    @RequestMapping("/detail")
    public String workbenchChartTransactionDetail(Model model,String id){
        Tran tran = tranService.getTranById(id);
        List<DicValue> stage = (List<DicValue>) redisTemplate.opsForValue().get("stage");
        Object source = redisTemplate.opsForValue().get("source");
        Object transactionType = redisTemplate.opsForValue().get("transactionType");
        if (tran.getStage()!=null){
            Object possibility = redisTemplate.opsForHash().get("possibility",tran.getStage());
            model.addAttribute("possibility",possibility);
        }
        model.addAttribute("stageList",stage);
        model.addAttribute("sourceList",source);
        model.addAttribute("transactionTypeList",transactionType);
        model.addAttribute("tran",tran);

        return "workbench/transaction/detail";
    }
    @RequestMapping("/save")
    public String workbenchChartTransactionSave(Model model){
        Object stage =redisTemplate.opsForValue().get("stage");
        Object source = redisTemplate.opsForValue().get("source");
        Object transactionType = redisTemplate.opsForValue().get("transactionType");


        //Tran tran = clueService.getClue(id);
        model.addAttribute("stageList",stage);
        model.addAttribute("sourceList",source);
        model.addAttribute("transactionTypeList",transactionType);
        return "workbench/transaction/save";
    }
    @RequestMapping("/edit")
    public String workbenchChartTransactionEdit(String id,Model model){
        Tran tran = tranService.selectiveTran(id);
        Object stage =redisTemplate.opsForValue().get("stage");
        Object source = redisTemplate.opsForValue().get("source");
        Object transactionType = redisTemplate.opsForValue().get("transactionType");
        /*Set<String> set = map.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            model.addAttribute(key,map.get(key));
            System.out.println("key="+key);
            System.out.println("values="+map.get(key));
        }*/
        //Tran tran = clueService.getClue(id);
        model.addAttribute("stageList",stage);
        model.addAttribute("sourceList",source);
        model.addAttribute("transactionTypeList",transactionType);
        model.addAttribute("tran",tran);
        return "workbench/transaction/edit";
    }
    @RequestMapping("/getActivityListByName")
    @ResponseBody
    public Map<String,Object> getActivityListByName(String name){
        System.out.println(name);
        List<Activity> activityList =tranService.getActivityListByName(name);
        System.out.println(activityList);
        Map<String,Object> map = new HashMap<>();
        map.put("activityList",activityList);
        return map;
    }

    @RequestMapping("/getContactsListByName")
    @ResponseBody
    public Map<String,Object> getContactsListByName(String name){
        System.out.println(name);
        List<Contacts> contactsList =tranService.getContactsListByName(name);
        System.out.println(contactsList);
        Map<String,Object> map = new HashMap<>();
        map.put("contactsList",contactsList);
        return map;
    }
    @RequestMapping("/getCustomerName")
    @ResponseBody
    public List<String> getCustomerName(String name){
        System.out.println(name);
        List<String> names = tranService.getCustomerName(name);
        return names;
    }

    @RequestMapping("/getPossibility")
    @ResponseBody
    public String getPossibility(String value){
        String possibility = (String) redisTemplate.opsForHash().get("possibility", value);
        return possibility;
    }
    @RequestMapping("/pageList")
    @ResponseBody
    public PaginationVO<Tran> pageList(String pageNo, String pageSize, Tran tran){
        System.out.println("**************"+tran);
        PaginationVO<Tran> paginationVO = tranService.pageList(pageNo,pageSize,tran);
        return paginationVO;
    }
    @RequestMapping("/saveTran")
    public String saveTran(Tran tran, HttpServletRequest request){
        System.out.println(tran);
        User user = (User) request.getSession().getAttribute("user");
        tran.setId(UUIDUtil.getUUID());
        tran.setCreateBy(user.getName());
        tran.setCreateTime(DateTimeUtil.getSysTime());
        boolean flag = tranService.saveTran(tran);
        return "workbench/transaction/index";
    }

    @RequestMapping("/updateTran")
    public String updateTran(Tran tran,HttpServletRequest request){
        System.out.println(tran);
        User user = (User) request.getSession().getAttribute("user");
        tran.setEditBy(user.getName());
        tran.setEditTime(DateTimeUtil.getSysTime());
        boolean flag = tranService.updateTran(tran);
        return "redirect:/workbench/transaction/index";
    }
    @RequestMapping("/tranHistoryList")
    @ResponseBody
    public List<TranHistory> tranHistoryList(String tranId){
        System.out.println(tranId);
        List<TranHistory> tranHistoryList = tranService.tranHistoryList(tranId);
        for (TranHistory tranHistory:tranHistoryList) {
            String stage = tranHistory.getStage();
            if (stage==null){
                break;
            }
            String possibility = (String) redisTemplate.opsForHash().get("possibility", stage);
            tranHistory.setPossibility(possibility);
        }
        return tranHistoryList;
    }

}
