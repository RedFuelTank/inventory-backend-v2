package com.netgroup.config;

import com.netgroup.domain.client.ClientServiceImpl;
import com.netgroup.domain.client.api.ClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfiguration {
    @Bean
    public ClientService getClientService() {
        return new ClientServiceImpl();
    }
}
