package com.zx.crm.workbench.mapper;

import com.zx.crm.workbench.model.Activity;
import com.zx.crm.workbench.model.PageList;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    int deleteByPrimaryKey(String id);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);

    int getActivityCounts(PageList pageList);

    List<Activity> selectiveActivity(PageList pageList);

    int deleteActivitiesById(String[] id);

    Activity selectById(String id);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityByNotHaveClueId(Map<String, String> map);

    List<Activity> getActivityListByName(String name);
}