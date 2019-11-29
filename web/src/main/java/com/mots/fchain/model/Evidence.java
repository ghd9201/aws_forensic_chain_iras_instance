package com.mots.fchain.model;

import lombok.Data;

@Data
public class Evidence {
    private String objectId;
    private String registerTime;
    private String register;
    private String caseId;
    private String evidenceId;
    private String fileName;
    private String fileSize;
    private String description;
    private String hash;
}
