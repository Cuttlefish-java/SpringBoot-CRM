package com.zx.crm.settings.service.impl;

import com.zx.crm.settings.mapper.DicTypeMapper;
import com.zx.crm.settings.mapper.DicValueMapper;
import com.zx.crm.settings.model.DicType;
import com.zx.crm.settings.model.DicValue;
import com.zx.crm.settings.service.DicTypeService;
import com.zx.crm.settings.service.DicValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class DicValueServiceImpl implements DicValueService {
    @Autowired
    private DicValueMapper dicValueMapper;
    @Autowired
    private DicTypeMapper dicTypeMapper;
    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String, List<DicValue>> map = new HashMap<>();
        List<DicType> dicTypes = dicTypeMapper.getAll();
        for (DicType dicType:dicTypes){
            List<DicValue> dicValues= dicValueMapper.selectByTypeCode(dicType.getCode());
            map.put(dicType.getCode(),dicValues);
        }
        return map;
    }
    public void setDicValueTORedis(){
        Map<String,List<DicValue>> map= getAll();
        Set<String> set = map.keySet();

            for (String key:set){
                Boolean dicValue = redisTemplate.hasKey(key);
                System.out.println(dicValue+"...........");
                System.out.println(key);
                if (!dicValue){
                    redisTemplate.opsForValue().set(key,map.get(key));
                }
                Object dicValue1 = redisTemplate.opsForValue().get(key);
                System.out.println(dicValue1);
            }
    }
    @Override
    public void addPossibility() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Stage2Possibility");
        //Map<String,String> possibility = new HashMap<>();
        Set<String> set = resourceBundle.keySet();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            String value = resourceBundle.getString(key);
            System.out.println("key==="+key+"value==="+value);
            redisTemplate.opsForHash().put("possibility",key,value);
        }


    }
    @Override
    public void flushDicToRedis() {
        Set<Object> keys = redisTemplate.keys("*");
        if (keys!=null){
            redisTemplate.delete(keys);
        }
    }
}
