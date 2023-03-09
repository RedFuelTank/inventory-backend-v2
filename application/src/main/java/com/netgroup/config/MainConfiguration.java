package com.netgroup.config;

import com.netgroup.usecase.client.ClientServiceImpl;
import com.netgroup.usecase.client.api.ClientRepository;
import com.netgroup.usecase.client.api.ClientService;
import com.netgroup.usecase.inventory_system.InventorySystemServiceImpl;
import com.netgroup.usecase.inventory_system.api.InventorySystemService;
import com.netgroup.usecase.inventory_system.api.ItemRepository;
import com.netgroup.usecase.inventory_system.api.StorageRepository;
import com.netgroup.usecase.payment.PaymentServiceImpl;
import com.netgroup.usecase.payment.api.PaymentRepository;
import com.netgroup.usecase.payment.api.PaymentService;
import com.netgroup.usecase.statistics.StatisticsServiceImpl;
import com.netgroup.usecase.statistics.api.StatisticsRepository;
import com.netgroup.usecase.statistics.api.StatisticsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfiguration {
    @Bean
    public ClientService clientService(ClientRepository clientRepository) {
        return new ClientServiceImpl(clientRepository);
    }

    @Bean
    public InventorySystemService inventorySystemService(ItemRepository itemRepository, StorageRepository storageRepository) {
        return new InventorySystemServiceImpl(storageRepository, itemRepository);
    }

    @Bean
    public PaymentService paymentService(PaymentRepository paymentRepository) {
        return new PaymentServiceImpl(paymentRepository);
    }

    @Bean
    public StatisticsService statisticsService(StatisticsRepository statisticsRepository) {
        return new StatisticsServiceImpl(statisticsRepository);
    }
}
