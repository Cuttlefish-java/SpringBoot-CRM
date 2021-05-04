package com.zx.crm.workbench.mapper;

import com.zx.crm.workbench.model.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationMapper {
    int deleteByPrimaryKey(String id);

    int insert(ClueActivityRelation record);

    int insertSelective(ClueActivityRelation record);

    ClueActivityRelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ClueActivityRelation record);

    int updateByPrimaryKey(ClueActivityRelation record);

    List<ClueActivityRelation> selectActivityByClueId(String clueId);

    int deleteByClueId(String clueId);

    int getCountsByClueId(String clueId);
}