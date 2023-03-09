package com.netgroup.usecase.statistics.api;

import com.netgroup.usecase.inventory_system.api.ItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface StatisticsService {
    Page<StatisticsDto> getStatisticsFromTo(String businessName,
                                            Date from,
                                            Date to,
                                            Pageable pageable);

    Page<StatisticsDto> getStatisticsFrom(String businessName,
                                          Date from,
                                          Pageable pageable);

    void addItemToStatistics(ItemDto itemDto, String businessName);
}
