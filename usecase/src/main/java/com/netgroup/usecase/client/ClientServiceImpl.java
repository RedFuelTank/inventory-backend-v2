package com.netgroup.usecase.client;

import com.netgroup.entity.client.Business;
import com.netgroup.entity.client.Representative;
import com.netgroup.usecase.client.api.BusinessDto;
import com.netgroup.usecase.client.api.ClientRepository;
import com.netgroup.usecase.client.api.ClientService;
import com.netgroup.usecase.client.api.RepresentativeDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository repository;

    @Override
    public RepresentativeDto registerUser(RepresentativeDto representativeDto) {
        Representative register = repository.register(Representative.builder()
                .username(representativeDto.getUsername())
                .password(representativeDto.getPassword())
                .build());

        return RepresentativeDto.builder()
                .username(register.getUsername())
                .password(register.getPassword())
                .build();
    }

    @Override
    public BusinessDto registerBusiness(String representativeUsername, BusinessDto businessDto) {
        Business business = Business.builder()
                .name(businessDto.getName())
                .password(businessDto.getPassword())
                .representative(repository.getRepresentativeByUsername(representativeUsername)
                        .orElseThrow(IllegalArgumentException::new))
                .build();
        repository.registerBusiness(business);
        return businessDto;
    }
}
