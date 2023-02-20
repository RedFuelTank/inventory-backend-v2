package com.netgroup.domain.client;

import com.netgroup.domain.client.api.ClientService;

public class ClientServiceImpl implements ClientService {
    @Override
    public String test() {
        return "Impl";
    }
}
