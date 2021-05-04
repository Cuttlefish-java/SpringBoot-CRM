package com.zx.crm.workbench.mapper;

import com.zx.crm.workbench.model.Contacts;

import java.util.List;

public interface ContactsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Contacts record);

    int insertSelective(Contacts record);

    Contacts selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Contacts record);

    int updateByPrimaryKey(Contacts record);

    List<Contacts> getContactsListByName(String name);
}