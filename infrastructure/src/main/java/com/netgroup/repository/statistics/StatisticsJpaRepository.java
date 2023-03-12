package com.netgroup.repository.statistics;

import com.netgroup.entity.inventory_system.Item;
import com.netgroup.entity.statistics.Statistics;
import com.netgroup.repository.statistics.model.StatisticsModel;
import com.netgroup.usecase.statistics.api.StatisticsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface StatisticsJpaRepository extends StatisticsRepository,
        PagingAndSortingRepository<StatisticsModel, Long>,
        JpaRepository<StatisticsModel, Long> {
    @Override
    @Query(value = "select o from StatisticsModel o where o.businessName = :businessName and o.startDate >= :from and o.endDate <= :to")
    Page<Statistics> getStatisticsFromTo(@Param("businessName") String businessName,
                                         @Param("from") Date from,
                                         @Param("to") Date to,
                                         Pageable pageable);


    @Override
    @Query(value = "select o from StatisticsModel o where o.businessName = :businessName and o.startDate >= :from")
    Page<Statistics> getStatisticsFrom(
            @Param("businessName") String businessName,
            @Param("from") Date from,
            Pageable pageable);

    @Override
    default void addItemToStatistics(Item item) {
        save(StatisticsModel.builder()
                .itemId(item.getId())
                .businessName(item.getBusinessName())
                .name(item.getName())
                .startDate(new Date())
                .build());
    }

    @Override
    @Modifying
    @Transactional
    @Query("update StatisticsModel set endDate = CURRENT_TIMESTAMP where id = :id and businessName = :businessName")
    void setItemOutdated(@Param("id") Long id,
                         @Param("businessName") String businessName);
}
