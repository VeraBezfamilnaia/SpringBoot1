package com.bezf.springbootdemo.config;

import com.bezf.springbootdemo.DevProfile;
import com.bezf.springbootdemo.ProductionProfile;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {

    @ConditionalOnProperty(name = "netology.profile.dev", havingValue = "true", matchIfMissing = true)
    @Bean
    public DevProfile devProfile() {
        return new DevProfile();
    }

    @ConditionalOnProperty(name = "netology.profile.dev", havingValue = "false")
    @Bean
    public ProductionProfile productionProfile() {
        return new ProductionProfile();
    }
}
