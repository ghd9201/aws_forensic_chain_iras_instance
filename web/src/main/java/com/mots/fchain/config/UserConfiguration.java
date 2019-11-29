package com.mots.fchain.config;

import com.mots.fchain.service.UserService;
import com.mots.fchain.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {
    @Bean
    UserService memberService(){
        return new UserServiceImpl();
    }
}