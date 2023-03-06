package com.netgroup.entity.payment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Payment {
    @Builder.Default
    private Long freeAmount = 10L;
    @Builder.Default
    private Long totalAmount = 0L;
    private String businessName;

    public double getCredit() {
        return (totalAmount < freeAmount) ? 0
                : (totalAmount - freeAmount) * 0.01;
    }
}
