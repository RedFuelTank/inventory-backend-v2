package com.netgroup.web.client.provider;

import com.netgroup.entity.client.Business;
import com.netgroup.entity.client.Representative;
import com.netgroup.usecase.client.ClientServiceImpl;
import com.netgroup.usecase.client.api.BusinessDto;
import com.netgroup.usecase.client.api.ClientRepository;
import com.netgroup.usecase.client.api.ClientService;
import com.netgroup.usecase.client.api.RepresentativeDto;
import com.netgroup.usecase.payment.api.PaymentService;

import java.util.Optional;

public class ClientImplProvider {
    public static ClientService clientService() {
        return new ClientService() {
            @Override
            public RepresentativeDto registerUser(RepresentativeDto representativeDto) {
                return null;
            }

            @Override
            public BusinessDto registerBusiness(String representativeUsername, BusinessDto businessDto) {
                return null;
            }
        };
    }

    public static ClientRepository clientRepository() {
        return new ClientRepository() {
            @Override
            public Representative register(Representative representative) {
                return null;
            }

            @Override
            public Business registerBusiness(Business business) {
                return null;
            }

            @Override
            public Optional<Representative> getRepresentativeByUsername(String representativeUsername) {
                return Optional.empty();
            }
        };
    }

    public static PaymentService paymentService() {
        return new PaymentService() {
            @Override
            public double getCredit(String businessName) {
                return 0;
            }

            @Override
            public void incrementTotalAmount(String businessName) {

            }

            @Override
            public void decrementTotalAmount(String businessName) {

            }

            @Override
            public void createPayment(String businessName) {

            }
        };
    }
}
