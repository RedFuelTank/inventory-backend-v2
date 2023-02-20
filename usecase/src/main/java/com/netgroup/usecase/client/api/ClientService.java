package com.netgroup.usecase.client.api;

public interface ClientService {
    RepresentativeDto registerUser(RepresentativeDto representativeDto);

    BusinessDto registerBusiness(RepresentativeDto representativeDto, BusinessDto businessDto);
}
