package com.zx.crm.workbench.service.impl;

import com.github.pagehelper.PageHelper;
import com.zx.crm.exception.SaveActivityException;
import com.zx.crm.settings.mapper.UserMapper;
import com.zx.crm.settings.model.User;
import com.zx.crm.workbench.mapper.ActivityMapper;
import com.zx.crm.workbench.mapper.ActivityRemarkMapper;
import com.zx.crm.workbench.model.Activity;
import com.zx.crm.workbench.model.PageList;
import com.zx.crm.vo.PaginationVO;
import com.zx.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;

    @Override
    public List<Activity> getActivityByNotHaveClueId(Map<String, String> map) {
        List<Activity> activities=activityMapper.getActivityByNotHaveClueId(map);
        return activities;
    }

    @Override
    public List<Activity> getActivityListByName(String name) {
        List<Activity> activities = activityMapper.getActivityListByName(name);
        return activities;
    }

    @Autowired
    private UserMapper userMapper;
    @Transactional
    @Override
    public Activity detail(String id) {
        Activity activity = activityMapper.selectById(id);
        return activity;
    }

    @Transactional
    @Override
    public boolean updateActivity(Activity activity) {
        boolean flag = false;
        int result = activityMapper.updateByPrimaryKeySelective(activity);
        System.out.println(result);
        if (result==1){
            flag = true;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getActivityAndUserListByActivityId(String id) {
        List<User> userList = userMapper.getUserList();
        Activity activity = activityMapper.selectByPrimaryKey(id);
        Map<String ,Object> map = new HashMap<>();
        map.put("userList",userList);
        map.put("activity",activity);
        return map;
    }

    @Transactional
    @Override
    public boolean deleteActivity(String[] id) {
        boolean flag = true;
        //查询市场活动备注表需要删除的记录数
        int count1 = activityRemarkMapper.getCountsByActivityId(id);
        int count2 = activityRemarkMapper.deleteByActivitiesId(id);
        if (count1!=count2){
            flag = false;
        }
        System.out.println("count1"+count1);
        System.out.println("count2"+count2);
        int count3 = activityMapper.deleteActivitiesById(id);
        if (count3!=id.length){
            flag = false;
        }
        System.out.println("count3"+count3);
        System.out.println(flag);
        return flag;
    }

    @Transactional
    @Override
    public PaginationVO selectiveActivity(PageList pageList) {
        PaginationVO<Activity> paginationVO = new PaginationVO<>();
        int total = activityMapper.getActivityCounts(pageList);
        System.out.println("total="+total);
        PageHelper.startPage(pageList.getPageNo(),pageList.getPageSize());
        List<Activity> activities = activityMapper.selectiveActivity(pageList);
        System.out.println(activities);
        paginationVO.setTotal(total);
        paginationVO.setDataList(activities);
        return paginationVO;
    }

    @Transactional
    @Override
    public int saveActivity(Activity activity) throws SaveActivityException {
        System.out.println();
        if (activity.getOwner()==null||"".equals(activity.getOwner())){
            throw new SaveActivityException("所有者不能为空");
        }
        if (activity.getName()==null||"".equals(activity.getName())){
            throw new SaveActivityException("活动名称不能为空");
        }
        if (activity.getStartDate()==null||"".equals(activity.getStartDate())){
            throw new SaveActivityException("开始时间不能为空");
        }
        if (activity.getEndDate()==null||"".equals(activity.getEndDate())){
            throw new SaveActivityException("结束时间不能为空");
        }
        if (activity.getCost()==null||"".equals(activity.getCost())){
            throw new SaveActivityException("成本不能为空");
        }
        if (activity.getDescription()==null||"".equals(activity.getDescription())){
            throw new SaveActivityException("描述不能为空");
        }

        int result = activityMapper.insertSelective(activity);
        if (result == 0 ){
            throw new SaveActivityException("添加失败，请仔细检查添加内容");
        }
        return result;
    }
}
