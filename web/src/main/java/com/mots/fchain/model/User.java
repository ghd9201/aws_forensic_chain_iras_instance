package com.mots.fchain.model;


import lombok.Data;

@Data
public class User {
    private String userId;
    private String password;
    private String name;
    /* 회원가입 일자 */
    private String date;
    /* 직급 */
    private String org;

    public User(String UserId, String password, String name, String date, String org){
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.date = date;
        this.org = org;
    }

    public User(User user){
        this.userId = user.userId;
        this.password = user.password;
        this.name = user.name;
        this.date = user.date;
        this.org = user.org;
    }

}
