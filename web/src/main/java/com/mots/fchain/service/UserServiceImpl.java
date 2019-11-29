package com.mots.fchain.service;

import com.mots.fchain.mapper.EvidenceMapper;
import com.mots.fchain.mapper.UserMapper;
import com.mots.fchain.model.Evidence;
import com.mots.fchain.model.User;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /* 회원 가입 */
    public void signup(User user) throws Exception{
        userMapper.insertUser(user);
    }

    /* 회원 로그인 */
    public User login(String userId, String password) throws Exception {
        /*  회원 DB 정보 가져오기 */
        User user = userMapper.selectUser(userId);

        if(user != null) {
            /* 해당 이메일 회원이 있을 경우 */
            if (user.getPassword().equals(password)) {
                /* 로그인 성공 */
                return user;
            }else{
                System.out.println("password is wrong.");
                /* 패스워드가 틀린 경우 */
                throw new Exception();
            }
        }else{
            System.out.println("userId is wrong.");
            /* 해당 이메일 회원이 없을 경우 */
            throw new Exception();
        }
    }
}



