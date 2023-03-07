package com.netgroup.usecase.payment.api;

import com.netgroup.entity.payment.Payment;

public interface PaymentService {
    double getCredit(String businessName);
    void incrementTotalAmount(String businessName);
    void decrementTotalAmount(String businessName);
    void createPayment(String businessName);
}
