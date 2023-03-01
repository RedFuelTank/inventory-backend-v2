package com.netgroup.config;

import com.netgroup.usecase.client.ClientServiceImpl;
import com.netgroup.usecase.client.api.ClientRepository;
import com.netgroup.usecase.client.api.ClientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfiguration {
    @Bean
    public ClientService clientService(ClientRepository clientRepository) {
        return new ClientServiceImpl(clientRepository);
    }
}
