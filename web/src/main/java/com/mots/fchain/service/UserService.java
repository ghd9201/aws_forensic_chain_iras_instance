package com.mots.fchain.service;

import com.mots.fchain.model.User;

public interface UserService {
    /* 회원 가입 */
    void signup(User user) throws Exception;

    /* 회원 로그인 */
    User login(String userId, String password) throws Exception;
}
