package com.netgroup.usecase.client.api;

import com.netgroup.entity.client.Business;
import com.netgroup.entity.client.Representative;

import java.util.Optional;

public interface ClientRepository {
    Representative register(Representative representative);

    Business registerBusiness(Business business);

    Optional<Representative> getRepresentativeByUsername(String representativeUsername);
}
