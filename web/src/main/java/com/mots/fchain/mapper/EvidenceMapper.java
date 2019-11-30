package com.mots.fchain.mapper;

import com.mots.fchain.model.Evidence;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface EvidenceMapper {

    void insertEvidence(Evidence evidence) throws Exception;

    ArrayList<HashMap> selectAllEvidences() throws Exception;

    Evidence selectEvidence(String evidenceId) throws Exception;
}
