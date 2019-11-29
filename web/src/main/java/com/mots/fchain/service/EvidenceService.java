package com.mots.fchain.service;

import com.mots.fchain.model.Evidence;

import java.util.ArrayList;
import java.util.HashMap;

public interface EvidenceService {

    void upload(Evidence evidence) throws Exception;

    ArrayList<HashMap> selectAllEvidences() throws Exception;

    int getEvidenceNo(String caseId) throws Exception;

    Evidence selectEvidence(String caseId, int evidenceNo) throws Exception;
}
