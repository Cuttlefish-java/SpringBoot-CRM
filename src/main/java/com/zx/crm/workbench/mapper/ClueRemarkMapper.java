package com.zx.crm.workbench.mapper;

import com.zx.crm.workbench.model.Activity;
import com.zx.crm.workbench.model.ClueRemark;

import java.util.List;

public interface ClueRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(ClueRemark record);

    int insertSelective(ClueRemark record);

    ClueRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ClueRemark record);

    int updateByPrimaryKey(ClueRemark record);
    

    int getCountsByCluesId(String[] id);

    int deleteByCluesId(String[] id);

    List<ClueRemark> getRemarksByClueId(String clueId);

    int deleteByClueId(String clueId);

    int getCountsByClueId(String clueId);
}