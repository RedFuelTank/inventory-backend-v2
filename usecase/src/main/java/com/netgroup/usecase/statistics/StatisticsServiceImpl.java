package com.netgroup.usecase.statistics;

import com.netgroup.entity.inventory_system.Item;
import com.netgroup.entity.statistics.Statistics;
import com.netgroup.usecase.inventory_system.api.ItemDto;
import com.netgroup.usecase.statistics.api.StatisticsDto;
import com.netgroup.usecase.statistics.api.StatisticsRepository;
import com.netgroup.usecase.statistics.api.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository repository;

    @Override
    public Page<StatisticsDto> getStatisticsFromTo(String businessName, Date from, Date to, Pageable pageable) {
        Page<Statistics> statisticsPage = repository.getStatisticsFromTo(businessName, from, to, pageable);
        List<Statistics> statisticsPageContent = statisticsPage.getContent();

        return new PageImpl<>(
                statisticsPageContent.stream().map(e -> StatisticsDto.builder()
                        .itemId(e.getItemId())
                        .businessName(e.getBusinessName())
                        .startDate(e.getStartDate())
                        .endDate(e.getEndDate())
                        .build()).toList(),
                pageable,
                statisticsPage.getTotalElements());
    }

    @Override
    public Page<StatisticsDto> getStatisticsFrom(String businessName, Date from, Pageable pageable) {
        Page<Statistics> statisticsPage = repository.getStatisticsFrom(businessName, from, pageable);
        List<Statistics> statisticsPageContent = statisticsPage.getContent();
        return new PageImpl<>(
                statisticsPageContent.stream().map(e -> StatisticsDto.builder()
                        .itemId(e.getItemId())
                        .businessName(e.getBusinessName())
                        .startDate(e.getStartDate())
                        .endDate(e.getEndDate())
                        .build()).toList(),
                pageable,
                statisticsPage.getTotalElements()
        );
    }

    @Override
    public void addItemToStatistics(ItemDto itemDto, String businessName) {
        repository.addItemToStatistics(Item.builder()
                        .id(itemDto.getId())
                .businessName(businessName)
                .storageId(itemDto.getStorageId())
                .name(itemDto.getName())
                .build());
    }

    @Override
    public void setItemOutdated(Long id, String businessName) {
        repository.setItemOutdated(id, businessName);
    }
}
