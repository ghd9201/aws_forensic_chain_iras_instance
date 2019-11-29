package com.mots.fchain.controller.error;

public class MemberException extends Exception {

    private final int errorType;

    public MemberException(){
        this.errorType = 0;
    }

    public MemberException(int errorType){
        this.errorType = errorType;
    }
}
