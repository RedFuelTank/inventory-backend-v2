package com.netgroup.repository.payment;

import com.netgroup.entity.payment.Payment;
import com.netgroup.repository.payment.model.PaymentModel;
import com.netgroup.usecase.payment.api.PaymentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public void incrementTotalAmount(String businessName) {
        PaymentModel payment = manager.find(PaymentModel.class, businessName);
        payment.setTotalAmount(payment.getTotalAmount() + 1);

        manager.merge(payment);
    }

    @Override
    @Transactional
    public void decrementTotalAmount(String businessName) {
        PaymentModel payment = manager.find(PaymentModel.class, businessName);

        if (payment.getTotalAmount() == 0) {
            throw new IllegalArgumentException();
        }

        payment.setTotalAmount(payment.getTotalAmount() - 1);
        manager.merge(payment);
    }

    @Override
    @Transactional
    public void createPayment(Payment payment) {
        PaymentModel paymentModel = PaymentModel.builder()
                .businessName(payment.getBusinessName())
                .freeAmount(payment.getFreeAmount())
                .totalAmount(payment.getTotalAmount())
                .build();

        manager.persist(paymentModel);
    }

    @Override
    @Transactional
    public Payment getPaymentFor(String businessName) {
        PaymentModel paymentModel = manager.find(PaymentModel.class, businessName);
        return Payment.builder()
                .businessName(paymentModel.getBusinessName())
                .freeAmount(paymentModel.getFreeAmount())
                .totalAmount(paymentModel.getTotalAmount())
                .build();
    }
}
