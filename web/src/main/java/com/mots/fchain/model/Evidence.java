package com.mots.fchain.model;

import lombok.Data;

@Data
public class Evidence {
    private int objectId;
    private String registerTime;
    private String registerId;
    private String caseId;
    private String evidenceId;
    private String fileName;
    private long fileSize;
    private String filePath;
    private String description;
    private String hash;
}
