package com.mots.fchain.mapper;

import com.mots.fchain.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> selectUserList() throws Exception;

    void insertUser(User user) throws Exception;

    User selectUser(String userId) throws Exception;

    void updateUser(User user) throws Exception;

    void deleteUser(String userId) throws Exception;
}
