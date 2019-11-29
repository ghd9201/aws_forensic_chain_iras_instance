package com.mots.fchain.model;

import lombok.Data;

@Data
public class Case {

    private int seq;                        //db auto incremental
    private String caseId;
    private String startDate;
    private String endDate;
    private String status;                  //end or not

}
