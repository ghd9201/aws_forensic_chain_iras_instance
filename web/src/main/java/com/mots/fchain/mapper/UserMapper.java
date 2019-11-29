package com.mots.fchain.mapper;

import com.mots.fchain.model.User;

import java.util.List;

public interface UserMapper {
    List<User> selectUserList() throws Exception;

    void insertUser(User user) throws Exception;

    User selectUser(String userId) throws Exception;

    void updateUser(User user) throws Exception;

    void deleteUser(String userId) throws Exception;
}
