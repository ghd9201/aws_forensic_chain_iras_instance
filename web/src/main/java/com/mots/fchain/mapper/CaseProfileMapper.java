package com.mots.fchain.mapper;

import com.mots.fchain.model.Case;

public interface CaseProfileMapper {
    void insertCase(String caseId) throws Exception;

    int getCaseCnt() throws Exception;
}
