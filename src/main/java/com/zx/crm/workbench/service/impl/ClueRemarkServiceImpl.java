package com.zx.crm.workbench.service.impl;

import com.zx.crm.workbench.mapper.ActivityMapper;
import com.zx.crm.workbench.mapper.ClueActivityRelationMapper;
import com.zx.crm.workbench.mapper.ClueRemarkMapper;
import com.zx.crm.workbench.model.Activity;
import com.zx.crm.workbench.model.ClueRemark;
import com.zx.crm.workbench.service.ClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClueRemarkServiceImpl implements ClueRemarkService {
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired 
    private ClueActivityRelationMapper clueActivityRelationMapper;
    @Override
    public boolean deleteClueRemark(String id) {
        int result = clueRemarkMapper.deleteByPrimaryKey(id);
        if (result>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteActivityById(String id) {
        int result = clueActivityRelationMapper.deleteByPrimaryKey(id);
        if (result==0){
            return false;
        }
        return true;
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueId) {
        List<Activity> activities  =activityMapper.getActivityListByClueId(clueId);
        return activities;
    }

    @Transactional
    @Override
    public boolean updateClueRemark(ClueRemark clueRemark) {
        int result = clueRemarkMapper.updateByPrimaryKeySelective(clueRemark);
        if (result>0){
            return true;
        }
        return false;
    }

    @Override
    public ClueRemark getClueRemarkById(String id) {
        ClueRemark clueRemark = clueRemarkMapper.selectByPrimaryKey(id);
        return clueRemark;
    }

    @Transactional
    @Override
    public boolean saveClueRemark(ClueRemark clueRemark) {
        int result = clueRemarkMapper.insertSelective(clueRemark);
        if (result>0){
            return true;
        }
        return false;
    }

    @Override
    public List<ClueRemark> getClueRemarks(String clueId) {
        List<ClueRemark> clueRemarks = clueRemarkMapper.getRemarksByClueId(clueId);
        return clueRemarks;
    }
}
