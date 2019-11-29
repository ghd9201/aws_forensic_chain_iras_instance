package com.mots.fchain.service;

import com.mots.fchain.model.Case;

public interface CaseService {
    /* 사건 생성 */
    void makeCase(String caseId) throws Exception;

    /* 사건 번호 얻어오기 */
    int getCaseCnt() throws Exception;
}
