package com.zx.crm.workbench.mapper;

import com.zx.crm.workbench.model.CustomerRemark;

import java.util.List;

public interface CustomerRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomerRemark record);

    int insertSelective(CustomerRemark record);

    CustomerRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerRemark record);

    int updateByPrimaryKey(CustomerRemark record);

    int getCustomerRemarks(String[] id);

    int deleteCustomerRemarks(String[] id);

    List<CustomerRemark> getRemarks(String customerId);
}