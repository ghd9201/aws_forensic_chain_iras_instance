package com.mots.fchain.mapper;

import com.mots.fchain.model.Evidence;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface EvidenceMapper {

    void insertEvidence(Evidence evidence) throws Exception;

    ArrayList<HashMap> selectAllEvidences() throws Exception;

    int getEvidenceNo(String caseId) throws Exception;

    Evidence selectEvidence(@Param("caseId") String caseId, @Param("evidenceNo") int evidenceNo);
}
