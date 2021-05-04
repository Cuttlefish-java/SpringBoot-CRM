package com.zx.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.zx.crm.settings.mapper.DicValueMapper;
import com.zx.crm.util.DateTimeUtil;
import com.zx.crm.util.UUIDUtil;
import com.zx.crm.vo.PaginationVO;
import com.zx.crm.workbench.mapper.*;
import com.zx.crm.workbench.model.*;
import com.zx.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class TranServiceImpl implements TranService {
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private TranHistoryMapper tranHistoryMapper;
    @Autowired
    private DicValueMapper dicValueMapper;

    @Override
    public List<Contacts> getContactsListByName(String name) {
        List<Contacts> contactsList = contactsMapper.getContactsListByName(name);
        return contactsList;
    }

    @Override
    public List<String> getCustomerName(String name) {
        List<String> names = customerMapper.getCustomerName(name);
        return names;
    }

    @Transactional
    @Override
    public boolean saveTran(Tran tran) {
        /*
            （1）添加页点击按钮提交表单,正常submit即可
		（2）提交表单客户上传名字，后台处理，最终保存的是customerId
		（3）查询是否有此客户，如果没有则添加客户记录
		（4）添加交易记录（注意先后顺序，添加完客户之后，才知道客户id）
		（5）添加交易历史
		（6）添加后重定向到列表页

        * */
        Customer customer = customerMapper.selectCustomer(tran.getCustomerId());
        if (customer==null){
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(tran.getOwner());
            customer.setName(tran.getName());
            customer.setCreateBy(tran.getCreateBy());
            customer.setCreateTime(tran.getCreateTime());
            customer.setNextContactTime(tran.getNextContactTime());

            tran.setCustomerId(customer.getId());

            int count1 = customerMapper.insertSelective(customer);
            if (count1!=1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }else {
            tran.setCustomerId(customer.getId());
        }

        //添加交易记录
        int count2 = tranMapper.insertSelective(tran);
        if (count2!=1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        //添加交易历史
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());
        tranHistory.setCreateBy(tran.getCreateBy());
        tranHistory.setCreateTime(tran.getCreateTime());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        int count3 = tranHistoryMapper.insertSelective(tranHistory);

        if (count3!=1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public Tran getTranById(String id) {
        Tran tran = tranMapper.selectTran(id);
        return tran;
    }
    @Transactional
    @Override
    public boolean updateTran(Tran tran) {
        Tran tran1 = tranMapper.selectByPrimaryKey(tran.getId());
        System.out.println(tran1);
        Customer customer = customerMapper.selectByPrimaryKey(tran1.getCustomerId());
        Contacts contacts = contactsMapper.selectByPrimaryKey(tran1.getContactsId());
        Activity activity = activityMapper.selectByPrimaryKey(tran1.getActivityId());
        customer.setName(tran.getCustomerId());
        customerMapper.updateByPrimaryKeySelective(customer);
        System.out.println("tran==========tran.getContactsId()"+tran.getContactsId());
        System.out.println(contacts);
        if (contacts==null){

            contacts = new Contacts();
            contacts.setId(UUIDUtil.getUUID());
            contacts.setOwner(tran.getOwner());
            contacts.setFullName(tran.getContactsId());
            contacts.setCreateBy(tran.getCreateBy());
            contacts.setCreateTime(DateTimeUtil.getSysTime());
            contacts.setNextContactTime(tran.getNextContactTime());

            int count1 = contactsMapper.insertSelective(contacts);
            if (count1!=1){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }else {
            /*contacts.setFullName(tran.getContactsId());
            contactsMapper.updateByPrimaryKeySelective(contacts);*/
            tran.setContactsId(contacts.getId());
        }
        if (activity!=null){
            tran.setActivityId(activity.getId());
        }
        tran.setCustomerId(customer.getId());
        tran.setContactsId(contacts.getId());
        //更新交易产生新的交易历史
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setCreateTime(DateTimeUtil.getSysTime());
        tranHistory.setCreateBy(tran.getEditBy());
        if (tran.getStage()!=null){
            tranHistory.setStage(tran.getStage());
        }
        int result1 = tranHistoryMapper.insertSelective(tranHistory);
        if (result1!=1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        //更新id字段问题，显示问题....
        int result2 = tranMapper.updateByPrimaryKeySelective(tran);
        if (result2!=1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public Tran selectiveTran(String id) {
        Tran tran = tranMapper.selectTran(id);

        return tran;
    }

    @Override
    public List<TranHistory> tranHistoryList(String tranId) {
        List<TranHistory> tranHistoryList = tranHistoryMapper.selectByTranId(tranId);

        return tranHistoryList;
    }

    @Transactional
    @Override
    public PaginationVO<Tran> pageList(String pageNO, String pageSize, Tran tran) {
        PaginationVO<Tran> paginationVO = new PaginationVO();
        int totals = tranMapper.getCounts(tran);
        System.out.println(totals);
        PageHelper.startPage(Integer.parseInt(pageNO),Integer.parseInt(pageSize));
        List<Tran> tranList = tranMapper.selectiveTrans(tran);
        paginationVO.setTotal(totals);
        paginationVO.setDataList(tranList);
        return paginationVO;
    }

    @Override
    public List<Activity> getActivityListByName(String name) {
        List<Activity> activityList = activityMapper.getActivityListByName(name);
        return activityList;
    }
}
