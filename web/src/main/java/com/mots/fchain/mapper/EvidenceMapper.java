package com.mots.fchain.mapper;

import com.mots.fchain.model.Evidence;

import java.util.ArrayList;
import java.util.HashMap;

public interface EvidenceMapper {

    void insertEvidence(Evidence evidence) throws Exception;

    ArrayList<HashMap> selectAllEvidences() throws Exception;
}
