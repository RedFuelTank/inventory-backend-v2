package com.netgroup.usecase.client;

import com.netgroup.usecase.client.api.BusinessDto;
import com.netgroup.usecase.client.api.ClientService;
import com.netgroup.usecase.client.api.RepresentativeDto;

public class ClientServiceImpl implements ClientService {
    @Override
    public RepresentativeDto registerUser(RepresentativeDto representativeDto) {
        return RepresentativeDto.builder()
                .name("Mock Client")
                .build();
    }

    @Override
    public BusinessDto registerBusiness(RepresentativeDto representativeDto, BusinessDto businessDto) {
        return BusinessDto.builder()
                .name("Mock Business")
                .password("Mock password")
                .build();
    }
}
