package com.zx.crm.workbench.mapper;

import com.zx.crm.workbench.model.Tran;

import java.util.List;
import java.util.Map;

public interface TranMapper {
    int deleteByPrimaryKey(String id);

    int insert(Tran record);

    int insertSelective(Tran record);

    Tran selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Tran record);

    int updateByPrimaryKey(Tran record);

    int getCounts(Tran tran);

    List<Tran> selectiveTrans(Tran tran);

    Tran selectTran(String id);

    List<Map<String, Object>> getCharts();
    int getTotal();
}