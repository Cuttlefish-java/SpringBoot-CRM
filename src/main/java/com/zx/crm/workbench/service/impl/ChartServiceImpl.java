package com.zx.crm.workbench.service.impl;

import com.zx.crm.vo.PaginationVO;
import com.zx.crm.workbench.mapper.TranMapper;
import com.zx.crm.workbench.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ChartServiceImpl implements ChartService {
    @Autowired
    private TranMapper tranMapper;
    @Transactional
    @Override
    public PaginationVO getEChart() {
        //取得total
        int total = tranMapper.getTotal();

        //取得dataList
        List<Map<String,Object>> dataList = tranMapper.getCharts();
        PaginationVO paginationVO = new PaginationVO();
        paginationVO.setTotal(total);
        paginationVO.setDataList(dataList);
        return paginationVO;
    }
}
