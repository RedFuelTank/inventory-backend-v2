package com.netgroup.usecase.payment;

import com.netgroup.entity.payment.Payment;
import com.netgroup.usecase.payment.api.PaymentRepository;
import com.netgroup.usecase.payment.api.PaymentService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repository;

    @Override
    public double getCredit(String businessName) {
        Payment payment = repository.getPaymentFor(businessName);
        return payment.getCredit();
    }

    @Override
    public void incrementTotalAmount(String businessName) {
        repository.incrementTotalAmount(businessName);
    }

    @Override
    public void decrementTotalAmount(String businessName) {
        repository.decrementTotalAmount(businessName);
    }

    @Override
    public void createPayment(String businessName) {
        Payment payment = Payment.builder()
                .businessName(businessName)
                .build();

        repository.createPayment(payment);
    }
}
