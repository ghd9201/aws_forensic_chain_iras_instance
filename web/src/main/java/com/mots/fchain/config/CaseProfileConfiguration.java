package com.mots.fchain.config;

import com.mots.fchain.service.CaseProfileService;
import com.mots.fchain.service.CaseProfileServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaseProfileConfiguration {
    @Bean
    CaseProfileService caseService() { return new CaseProfileServiceImpl(); }
}