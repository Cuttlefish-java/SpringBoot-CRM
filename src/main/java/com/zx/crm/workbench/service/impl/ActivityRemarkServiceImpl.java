package com.zx.crm.workbench.service.impl;

import com.zx.crm.workbench.mapper.ActivityRemarkMapper;
import com.zx.crm.workbench.model.ActivityRemark;
import com.zx.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;
    @Transactional
    @Override
    public ActivityRemark saveActivityRemarkBtn(ActivityRemark activityRemark) {
        int result = activityRemarkMapper.insertSelective(activityRemark);
        if (result>0) {
            ActivityRemark activityRemark1 = activityRemarkMapper.selectByPrimaryKey(activityRemark.getId());
            return  activityRemark1;
        }
        return null;
    }
    @Transactional
    @Override
    public ActivityRemark updateActivityRemark(ActivityRemark activityRemark) {
        int result = 0;
        ActivityRemark remark = activityRemarkMapper.selectByPrimaryKey(activityRemark.getId());
        if (remark!=null){
            remark.setEditBy(activityRemark.getEditBy());
            remark.setEditTime(activityRemark.getEditTime());
            remark.setNoteContent(activityRemark.getNoteContent());
            remark.setEditFlag("1");
            System.out.println(remark);
            result = activityRemarkMapper.updateByPrimaryKeySelective(activityRemark);
            if (result>0){
                remark = activityRemarkMapper.selectByPrimaryKey(activityRemark.getId());
                return remark;
            }
        }
        return null;
    }

    @Transactional
    @Override
    public ActivityRemark selectActivityRemarkById(String id) {
        ActivityRemark activityRemark = activityRemarkMapper.selectByPrimaryKey(id);

        return activityRemark;
    }

    @Transactional
    @Override
    public boolean deleteActivityRemark(String id) {
        boolean flag = true;
        int result = activityRemarkMapper.deleteByPrimaryKey(id);
        if (result==0){
            flag = false;
        }
        return flag;
    }

    @Override
    public List<ActivityRemark> getActivityRemarkByActivityId(String activityId) {
        List<ActivityRemark> activityRemarkList = activityRemarkMapper.selectActivityRemarkByActivityId(activityId);
        return activityRemarkList;
    }
}
