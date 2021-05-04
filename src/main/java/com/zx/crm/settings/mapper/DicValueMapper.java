package com.zx.crm.settings.mapper;

import com.zx.crm.settings.model.DicValue;

import java.util.List;

public interface DicValueMapper {
    int deleteByPrimaryKey(String id);

    int insert(DicValue record);

    int insertSelective(DicValue record);

    DicValue selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DicValue record);

    int updateByPrimaryKey(DicValue record);

/*    List<DicValue> getAll();*/

    List<DicValue> selectByTypeCode(String code);

    List<DicValue> getStageDicValueList();
}