package com.netgroup.usecase.payment.api;

import com.netgroup.entity.payment.Payment;

public interface PaymentRepository {
    void incrementTotalAmount(String businessName);

    void decrementTotalAmount(String businessName);

    void createPayment(Payment payment);

    Payment getPaymentFor(String businessName);
}
