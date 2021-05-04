package com.zx.crm.workbench.controller;

import com.zx.crm.vo.PaginationVO;
import com.zx.crm.workbench.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/workbench/chart")
public class ChartController {
    @Autowired
    private ChartService chartService;
    @RequestMapping("/transaction/index")
    public String workbenchChartTransactionIndex(){
        return "workbench/chart/transaction/index";
    }
    @RequestMapping("/transaction/getEChart")
    @ResponseBody
    public PaginationVO getEChart(){
        PaginationVO dicValuePaginationVO = chartService.getEChart();
        System.out.println(dicValuePaginationVO);
        return dicValuePaginationVO;
    }
}
