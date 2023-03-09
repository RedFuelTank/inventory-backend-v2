package com.netgroup.usecase.statistics.api;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class StatisticsDto {
    @NonNull
    private Long id;

    @NonNull
    private String businessName;

    private Long itemId;

    @NonNull
    private Date startDate;

    private Date endDate;
}
