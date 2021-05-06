package com.zx.crm.workbench.service;

import com.zx.crm.vo.PaginationVO;
import com.zx.crm.workbench.model.Customer;
import com.zx.crm.workbench.model.CustomerRemark;
import com.zx.crm.workbench.model.Tran;

import java.util.List;

public interface CustomerService {
    boolean saveCustomer(Customer customer);

    PaginationVO<Customer> pageList(Integer pageNo, Integer pageSize, Customer customer);

    Customer getCustomerById(String id);

    boolean updateCustomer(Customer customer);

    boolean deleteCustomer(String[] id);

    List<CustomerRemark> getRemarks(String customerId);

    Customer getCustomerById1(String customerId);

    boolean saveRemark(CustomerRemark customerRemark);

    CustomerRemark getRemark(String id);

    boolean updateCustomerRemark(CustomerRemark customerRemark);

    boolean deleteCustomerRemark(String id);

    List<Tran> showTranSactionList(String id);

    boolean deleteTranSactionById(String id);
}
