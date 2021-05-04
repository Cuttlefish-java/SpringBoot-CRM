package com.zx.crm.workbench.service;

import com.zx.crm.exception.SaveActivityException;
import com.zx.crm.workbench.model.Activity;
import com.zx.crm.workbench.model.PageList;
import com.zx.crm.vo.PaginationVO;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    int saveActivity(Activity activity) throws SaveActivityException;

    PaginationVO selectiveActivity(PageList pageList);

    boolean deleteActivity(String[] id);

    Map<String, Object> getActivityAndUserListByActivityId(String id);

    boolean updateActivity(Activity activity);

    Activity detail(String id);

    List<Activity> getActivityByNotHaveClueId(Map<String, String> map);

    List<Activity> getActivityListByName(String name);
}
