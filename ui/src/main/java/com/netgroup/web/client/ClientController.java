package com.netgroup.web.client;


import com.netgroup.usecase.client.api.BusinessDto;
import com.netgroup.usecase.client.api.ClientService;
import com.netgroup.usecase.client.api.RepresentativeDto;
import com.netgroup.usecase.payment.api.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final PaymentService paymentService;

    @PostMapping("/user/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public RepresentativeDto register(@RequestBody RepresentativeDto representativeDto) {
        return clientService.registerUser(representativeDto);
    }

    @PostMapping("/business/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public BusinessDto registerBusiness(@RequestBody BusinessDto businessDto, Authentication auth) {
        BusinessDto business = clientService.registerBusiness(auth.getName(), businessDto);
        paymentService.createPayment(business.getName());

        return business;
    }
}
