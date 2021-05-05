package com.zx.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.zx.crm.vo.PaginationVO;
import com.zx.crm.workbench.mapper.CustomerMapper;
import com.zx.crm.workbench.mapper.CustomerRemarkMapper;
import com.zx.crm.workbench.model.Customer;
import com.zx.crm.workbench.model.CustomerRemark;
import com.zx.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;

    @Transactional
    @Override
    public boolean updateCustomer(Customer customer) {
        int result = customerMapper.updateByPrimaryKeySelective(customer);
        if (result!=1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Transactional
    @Override
    public Customer getCustomerById(String id) {
        Customer customer = customerMapper.selectByPrimaryKey(id);
        System.out.println(customer);
        return customer;
    }
    @Transactional
    @Override
    public Customer getCustomerById1(String id) {
        Customer customer = customerMapper.getCustomerById(id);
        System.out.println(customer);
        return customer;
    }
    @Transactional
    @Override
    public boolean saveRemark(CustomerRemark customerRemark) {
        int result = customerRemarkMapper.insertSelective(customerRemark);
        if (result!=1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public CustomerRemark getRemark(String id) {
        return customerRemarkMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public PaginationVO<Customer> pageList(Integer pageNo, Integer pageSize, Customer customer) {
        PaginationVO<Customer> paginationVO = new PaginationVO<>();
        int totals = customerMapper.getCustomerCounts(customer);
        PageHelper.startPage(pageNo,pageSize);
        List<Customer> customers = customerMapper.selectiveCustomer(customer);
        paginationVO.setDataList(customers);
        paginationVO.setTotal(totals);
        return paginationVO;
    }
    @Transactional
    @Override
    public boolean deleteCustomer(String[] id) {
        int count1 = customerRemarkMapper.getCustomerRemarks(id);
        int count2 = customerRemarkMapper.deleteCustomerRemarks(id);
        if (count1!=count2){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        int count3 = customerMapper.deleteCustomers(id);
        System.out.println(count1);
        System.out.println(count2);
        System.out.println(count3);
        if (id.length!=count3){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }
    @Transactional
    @Override
    public boolean saveCustomer(Customer customer) {
        int result = customerMapper.insertSelective(customer);
        if (result!=1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public List<CustomerRemark> getRemarks(String customerId) {
        List<CustomerRemark> customerRemarks = customerRemarkMapper.getRemarks(customerId);
        return customerRemarks;
    }
}
