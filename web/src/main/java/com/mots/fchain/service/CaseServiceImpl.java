package com.mots.fchain.service;

import com.mots.fchain.mapper.CaseMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class CaseServiceImpl implements CaseService {

    @Autowired
    private CaseMapper caseMapper;

    /* 사건 생성 */
    public void makeCase(String caseId) throws Exception{
        caseMapper.insertCase(caseId);
    }

    /* 사건 번호 얻어오기 */
    public int getCaseCnt() throws Exception{
        return caseMapper.getCaseCnt();
    }
}
