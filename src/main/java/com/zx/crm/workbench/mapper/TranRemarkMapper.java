package com.zx.crm.workbench.mapper;

import com.zx.crm.workbench.model.TranRemark;

public interface TranRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(TranRemark record);

    int insertSelective(TranRemark record);

    TranRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TranRemark record);

    int updateByPrimaryKey(TranRemark record);
}