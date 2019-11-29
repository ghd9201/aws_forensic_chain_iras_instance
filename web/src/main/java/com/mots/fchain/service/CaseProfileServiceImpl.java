package com.mots.fchain.service;



import com.mots.fchain.mapper.CaseProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;


public class CaseProfileServiceImpl implements CaseProfileService {

    @Autowired
    private CaseProfileMapper caseProfileMapper;

    /* 사건 생성 */

    public void makeCase(String caseId) throws Exception{
        caseProfileMapper.insertCase(caseId);
    }

    /* 사건 번호 얻어오기 */
    public int getCaseCnt() throws Exception{
        return caseProfileMapper.getCaseCnt();
    }
}
