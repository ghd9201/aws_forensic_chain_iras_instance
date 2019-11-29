package com.mots.fchain.service;

import com.mots.fchain.mapper.EvidenceMapper;
import com.mots.fchain.model.Evidence;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;

public class EvidenceServiceImpl implements EvidenceService {

    @Autowired
    private EvidenceMapper evidenceMapper;

    public void upload(Evidence evidence) throws Exception{
        evidenceMapper.insertEvidence(evidence);
    }
    public ArrayList<HashMap> selectAllEvidences() throws Exception{
        return evidenceMapper.selectAllEvidences();
    }
}