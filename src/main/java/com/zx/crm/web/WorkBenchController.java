package com.zx.crm.web;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.zx.crm.workbench.model.Clue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/workbench")
public class WorkBenchController {
    @RequestMapping("/index")
    public String workBenchIndex(){
        return "workbench/index";
    }
    @RequestMapping("/main/index")
    public String workBenchMainIndex(){
        return "workbench/main/index";
    }
    @RequestMapping("/activity/index")
    public String workbenchActivityIndex(){
        return "workbench/activity/index";
    }
    @RequestMapping("/contacts/index")
    public String workbenchContactsIndex(){
        return "workbench/contacts/index";
    }
    @RequestMapping("/chart/activity/index")
    public String workbenchChartActivityIndex(){
        return "workbench/chart/activity/index";
    }
    @RequestMapping("/visit/index")
    public String workbenchVisitIndex(){
        return "workbench/visit/index";
    }
    @RequestMapping("/visit/detail")
    public String workbenchVisitDetail(){
        return "workbench/visit/detail";
    }
    @RequestMapping("/visit/editTask")
    public String workbenchVisitEditTask(){
        return "workbench/visit/editTask";
    }
    @RequestMapping("/visit/saveTask")
    public String workbenchVisitSaveTask(){
        return "workbench/visit/saveTask";
    }
    @RequestMapping("/chart/clue/index")
    public String workbenchChartClueIndex(){
        return "workbench/chart/clue/index";
    }
    @RequestMapping("/chart/customerAndContacts/index")
    public String workbenchChartCustomerAndContactsIndex(){
        return "workbench/chart/customerAndContacts/index";
    }

    @RequestMapping("/contacts/detail")
    public String workbenchChartContactsDetail(){
        return "workbench/contacts/detail";
    }

}
