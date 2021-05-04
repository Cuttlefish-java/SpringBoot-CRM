package com.zx.crm.workbench.mapper;

import com.zx.crm.workbench.model.Clue;

import java.util.List;

public interface ClueMapper {
    int deleteByPrimaryKey(String id);

    int insert(Clue record);

    int insertSelective(Clue record);

    Clue selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Clue record);

    int updateByPrimaryKey(Clue record);

    int getClueCounts( Clue clue);

    List<Clue> selectiveClue(Clue clue);

    int deleteCluesById(String[] id);

    Clue getClue(String id);
}