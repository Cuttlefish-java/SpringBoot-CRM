package com.zx.crm.settings.service;


import com.zx.crm.settings.model.DicValue;

import java.util.List;
import java.util.Map;

public interface DicValueService {
    Map<String, List<DicValue>> getAll();
    void setDicValueTORedis();
    void flushDicToRedis();
    void addPossibility();
}
