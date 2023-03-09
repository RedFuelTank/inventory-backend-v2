package com.netgroup.usecase.statistics.api;

import com.netgroup.entity.inventory_system.Item;
import com.netgroup.entity.statistics.Statistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface StatisticsRepository {
    Page<Statistics> getStatisticsFromTo(String businessName,
                                         Date from,
                                         Date to,
                                         Pageable pageable);

    Page<Statistics> getStatisticsFrom(String businessName,
                                          Date from,
                                          Pageable pageable);

    void addItemToStatistics(Item item);
}
