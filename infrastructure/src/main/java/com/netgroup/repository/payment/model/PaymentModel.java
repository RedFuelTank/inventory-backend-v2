package com.netgroup.repository.payment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class PaymentModel {
    @Id
    @Column(name = "business_name")
    private String businessName;

    @Column(name = "free_amount")
    private Long freeAmount;

    @Column(name = "total_amount")
    private Long totalAmount;
}
