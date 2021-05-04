package com.zx.crm.workbench.mapper;

import com.zx.crm.workbench.model.Customer;

import java.util.List;

public interface CustomerMapper {
    int deleteByPrimaryKey(String id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    int getCustomerCounts(Customer customer);

    List<Customer> selectiveCustomer(Customer customer);

    int deleteCustomers(String[] id);

    Customer getCustomerById(String id);

    Customer selectCustomer(String name);

    List<String> getCustomerName(String name);
}