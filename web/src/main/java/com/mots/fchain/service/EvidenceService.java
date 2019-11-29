package com.mots.fchain.service;

import com.mots.fchain.model.Evidence;

import java.util.ArrayList;
import java.util.HashMap;

public interface EvidenceService {

    void upload(Evidence evidence) throws Exception;

    ArrayList<HashMap> selectAllEvidences() throws Exception;
}
