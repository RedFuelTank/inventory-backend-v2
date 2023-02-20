package com.netgroup.usecase.client.api;

import com.netgroup.entity.client.Business;
import com.netgroup.entity.client.Representative;

public interface ClientRepository {
    Representative register(Representative representative);

    Business registerBusiness(Business business);
}
