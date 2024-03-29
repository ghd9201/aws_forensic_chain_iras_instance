package com.mots.fchain.config;

import com.mots.fchain.service.EvidenceService;
import com.mots.fchain.service.EvidenceServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EvidenceConfiguration {
    @Bean
    EvidenceService evidenceService(){
        return new EvidenceServiceImpl();
    }
}
