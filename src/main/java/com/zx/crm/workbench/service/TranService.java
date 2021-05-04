package com.zx.crm.workbench.service;

import com.zx.crm.settings.model.DicValue;
import com.zx.crm.vo.PaginationVO;
import com.zx.crm.workbench.model.Activity;
import com.zx.crm.workbench.model.Contacts;
import com.zx.crm.workbench.model.Tran;
import com.zx.crm.workbench.model.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService {
    List<Activity> getActivityListByName(String name);

    List<Contacts> getContactsListByName(String name);

    List<String> getCustomerName(String name);

    boolean saveTran(Tran tran);

    PaginationVO<Tran> pageList(String pageNO, String pageSize, Tran tran);

    Tran getTranById(String id);

    boolean updateTran(Tran tran);

    Tran selectiveTran(String id);

    List<TranHistory> tranHistoryList(String tranId);

    /*List<DicValue> getStageDicValueList();*/
}
