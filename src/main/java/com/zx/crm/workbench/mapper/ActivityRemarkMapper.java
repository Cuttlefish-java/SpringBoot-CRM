package com.zx.crm.workbench.mapper;

import com.zx.crm.workbench.model.ActivityRemark;

import java.util.List;

public interface ActivityRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(ActivityRemark record);

    int insertSelective(ActivityRemark record);

    ActivityRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ActivityRemark record);

    int updateByPrimaryKey(ActivityRemark record);

    int getCountsByActivityId(String[] id);

    int deleteByActivitiesId(String[] id);

    List<ActivityRemark> selectActivityRemarkByActivityId(String activityId);
}