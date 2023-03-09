package com.netgroup.web.client;


import com.netgroup.usecase.client.api.BusinessDto;
import com.netgroup.usecase.client.api.ClientService;
import com.netgroup.usecase.client.api.RepresentativeDto;
import com.netgroup.usecase.payment.api.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final PaymentService paymentService;

    @PostMapping("/user/register")
    public RepresentativeDto register(@RequestBody RepresentativeDto representativeDto) {
        return clientService.registerUser(representativeDto);
    }

    @PostMapping("/business/register")
    public BusinessDto registerBusiness(@RequestBody BusinessDto businessDto, Authentication auth) {
        BusinessDto business = clientService.registerBusiness(auth.getName(), businessDto);
        paymentService.createPayment(business.getName());

        return business;
    }
}
