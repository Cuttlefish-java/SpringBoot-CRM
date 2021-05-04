package com.zx.crm.workbench.service;

import com.zx.crm.workbench.model.Clue;
import com.zx.crm.settings.model.User;
import com.zx.crm.vo.PaginationVO;
import com.zx.crm.workbench.model.ClueActivityRelation;
import com.zx.crm.workbench.model.Tran;

import java.util.List;

public interface ClueService {
    List<User> getUserList();

    boolean saveClue(Clue clue);

    PaginationVO<Clue> selectiveClue(String pageNo, String pageSize, Clue clue);

    Clue getClueById(String id);

    boolean updateClue(Clue clue);

    boolean deleteClue(String[] id);

    Clue getClue(String id);

    boolean saveActivityBindClue(ClueActivityRelation clueActivityRelation);

    boolean clueConversion(String clueId, Tran tran, String createBy);
}
