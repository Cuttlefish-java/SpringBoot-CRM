package com.zx.crm.workbench.service;

import com.zx.crm.workbench.model.Activity;
import com.zx.crm.workbench.model.ClueRemark;

import java.util.List;

public interface ClueRemarkService {
    List<ClueRemark> getClueRemarks(String clueId);

    boolean saveClueRemark(ClueRemark clueRemark);

    ClueRemark getClueRemarkById(String id);

    boolean updateClueRemark(ClueRemark clueRemark);

    boolean deleteClueRemark(String id);

    List<Activity> getActivityListByClueId(String clueId);

    boolean deleteActivityById(String id);
}
