package com.zx.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.zx.crm.settings.mapper.UserMapper;
import com.zx.crm.util.DateTimeUtil;
import com.zx.crm.util.UUIDUtil;
import com.zx.crm.workbench.mapper.*;
import com.zx.crm.workbench.model.*;
import com.zx.crm.settings.model.User;
import com.zx.crm.vo.PaginationVO;
import com.zx.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Arrays;
import java.util.List;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;
    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;
    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private TranHistoryMapper tranHistoryMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;

    @Transactional
    @Override
    public boolean saveActivityBindClue(ClueActivityRelation clueActivityRelation) {

        String activityId1 = clueActivityRelation.getActivityId();
        List<String> list = Arrays.asList(activityId1.split(","));
        int count = 0;
        for (String activityId : list) {
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setActivityId(activityId);
            int result = clueActivityRelationMapper.insertSelective(clueActivityRelation);
            if (result == 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
            if (result == 1) {
                count++;
            }
        }
        return true;
    }

    @Transactional
    @Override
    public boolean clueConversion(String clueId, Tran tran, String createBy) {

        String crateTime = DateTimeUtil.getSysTime();
        //(1) 获取到线索id，通过线索id获取线索对象（线索对象当中封装了线索的信息）
        Clue clue = clueMapper.selectByPrimaryKey(clueId);
        System.out.println(clue);
        //(2) 通过线索对象提取客户信息，当该客户不存在的时候，新建客户（根据公司的名称精确匹配，判断该客户是否存在！）
        Customer customer = customerMapper.selectCustomer(clue.getCompany());
        System.out.println(customer);
        if (customer == null) {
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setAddress(clue.getAddress());
            customer.setOwner(clue.getOwner());
            customer.setContactSummary(clue.getContactSummary());
            customer.setDescription(clue.getDescription());
            customer.setName(clue.getCompany());
            customer.setCreateTime(crateTime);
            customer.setCreateBy(createBy);
            customer.setWebSite(clue.getWebSite());
            customer.setPhone(clue.getPhone());
            customer.setNextContactTime(clue.getNextContactTime());

            int count1 = customerMapper.insertSelective(customer);
            System.out.println("count1--------" + count1);
            if (count1 != 1) {
                // 回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }

        //(3) 通过线索对象提取联系人信息，保存联系人
        customer = customerMapper.selectCustomer(clue.getCompany());
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customer.getId());
        contacts.setFullName(clue.getFullName());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(crateTime);
        contacts.setDescription(clue.getDescription());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setAddress(clue.getAddress());
        int count2 = contactsMapper.insertSelective(contacts);
        System.out.println("count2----------" + count2);
        if (count2 != 1) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        //(4) 线索备注转换到客户备注以及联系人备注
        CustomerRemark customerRemark = new CustomerRemark();
        ContactsRemark contactsRemark = new ContactsRemark();
        //根据线索id查询线索备注
        List<ClueRemark> clueRemarkList = clueRemarkMapper.getRemarksByClueId(clueId);
        //遍历线索备注，在遍历的过程中创建客户备注以及联系人备注
        for (ClueRemark clueRemark : clueRemarkList) {
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(crateTime);
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setEditFlag("0");
            customerRemark.setNoteContent(clueRemark.getNoteContent());
            int count3 = customerRemarkMapper.insert(customerRemark);
            System.out.println("count3----------" + count3);
            if (count3 != 1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(crateTime);
            contactsRemark.setEditFlag("0");
            contactsRemark.setNoteContent(clueRemark.getNoteContent());
            int count4 = contactsRemarkMapper.insertSelective(contactsRemark);
            System.out.println("count4----------" + count4);
            if (count4 != 1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }

        //(5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系
        //根据线索id查询出相关的市场活动
        ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationMapper.selectActivityByClueId(clueId);
        for (ClueActivityRelation clueActivityRelation : clueActivityRelationList) {
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(clueActivityRelation.getActivityId());
            contactsActivityRelation.setContactsId(contacts.getId());
            int count5 = contactsActivityRelationMapper.insertSelective(contactsActivityRelation);
            System.out.println("count5----------" + count5);
            if (count5 != 1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }

        //(6) 如果有创建交易需求，创建一条交易
        if (tran.getId() != null) {
            tran.setOwner(clue.getOwner());
            tran.setCustomerId(customer.getId());
            int count6 = tranMapper.insertSelective(tran);
            System.out.println("count6----------" + count6);
            if (count6 != 1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }

            //(7) 如果创建了交易，则创建一条该交易下的交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setStage(tran.getStage());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setCreateTime(crateTime);
            tranHistory.setCreateBy(createBy);
            tranHistory.setTranId(tran.getId());
            int count7 = tranHistoryMapper.insertSelective(tranHistory);
            System.out.println("count7----------" + count7);
            if (count7 != 1) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
        }

        //(8) 删除线索备注
        int count8 = clueRemarkMapper.getCountsByClueId(clueId);
        int count9 = clueRemarkMapper.deleteByClueId(clueId);
        System.out.println("count8----------" + count8);
        System.out.println("count9----------" + count9);
        if (count8 != count9) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        ////(9) 删除线索和市场活动的关系
        int count11 = clueActivityRelationMapper.getCountsByClueId(clueId);
        int count10 = clueActivityRelationMapper.deleteByClueId(clueId);
        System.out.println("count10----------" + count10);
        System.out.println("count11----------" + count11);
        if (count10!=count11) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        System.out.println("******************");
        //(10)删除线索
        int count12 = clueMapper.deleteByPrimaryKey(clueId);
        System.out.println("******************");
        System.out.println("count12---------" + count12);
        if (count12 != 1) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public Clue getClue(String id) {
        return clueMapper.getClue(id);
    }


    @Transactional
    @Override
    public boolean saveClue(Clue clue) {
        boolean flag = true;
        int result = clueMapper.insertSelective(clue);
        if (result <= 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            flag = false;
        }
        return flag;
    }

    @Override
    public Clue getClueById(String id) {
        Clue clue = clueMapper.selectByPrimaryKey(id);
        return clue;
    }

    @Transactional
    @Override
    public boolean deleteClue(String[] id) {
        for (String s : id) {
            System.out.println(s);
        }
        int count1 = clueRemarkMapper.getCountsByCluesId(id);
        int count2 = clueRemarkMapper.deleteByCluesId(id);
        if (count1 != count2) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        System.out.println("count1" + count1);
        System.out.println("count2" + count2);
        int count3 = clueMapper.deleteCluesById(id);
        System.out.println("count3" + count3);
        if (count3 != id.length) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Transactional
    @Override
    public boolean updateClue(Clue clue) {
        int result = clueMapper.updateByPrimaryKeySelective(clue);
        if (result <= 0) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Transactional
    @Override
    public PaginationVO<Clue> selectiveClue(String pageNo, String pageSize, Clue clue) {
        PaginationVO<Clue> paginationVO = new PaginationVO<>();
        int count = clueMapper.getClueCounts(clue);
        PageHelper.startPage(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        List<Clue> clues = clueMapper.selectiveClue(clue);
        paginationVO.setTotal(count);
        paginationVO.setDataList(clues);
        return paginationVO;
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = userMapper.getUserList();
        return userList;
    }
}
