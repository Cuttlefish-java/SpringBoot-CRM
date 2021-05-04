package com.zx.crm.workbench.mapper;

import com.zx.crm.workbench.model.TranHistory;

import java.util.List;

public interface TranHistoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(TranHistory record);

    int insertSelective(TranHistory record);

    TranHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TranHistory record);

    int updateByPrimaryKey(TranHistory record);

    List<TranHistory> selectByTranId(String tranId);
}