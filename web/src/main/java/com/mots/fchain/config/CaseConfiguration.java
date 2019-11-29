package com.mots.fchain.config;

import com.mots.fchain.service.CaseService;
import com.mots.fchain.service.CaseServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaseConfiguration {
    @Bean
    CaseService caseService() {
        return new CaseServiceImpl();
    }
}