package com.zx.crm.workbench.service;

import com.zx.crm.workbench.model.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {
    List<ActivityRemark> getActivityRemarkByActivityId(String activityId);

    boolean deleteActivityRemark(String id);

    ActivityRemark  saveActivityRemarkBtn(ActivityRemark activityRemark);

    ActivityRemark selectActivityRemarkById(String id);

    ActivityRemark updateActivityRemark(ActivityRemark activityRemark);
}
