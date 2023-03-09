package com.netgroup.entity.statistics;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
public class Statistics {
    private Long id;
    private Long itemId;
    private String businessName;
    private Date startDate;
    private Date endDate;
}
