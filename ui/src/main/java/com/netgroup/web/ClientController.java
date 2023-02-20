package com.netgroup.web;


import com.netgroup.usecase.client.api.BusinessDto;
import com.netgroup.usecase.client.api.ClientService;
import com.netgroup.usecase.client.api.RepresentativeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClientController {
    private final ClientService service;

    @PostMapping("/register")
    public RepresentativeDto register(RepresentativeDto representativeDto) {
        return service.registerUser(representativeDto);
    }

    @PostMapping("/business/register")
    public BusinessDto registerBusiness(BusinessDto businessDto) {
        return service.registerBusiness(null, businessDto);
    }
}
