package com.netgroup.web.statistics;

import com.netgroup.usecase.statistics.api.StatisticsDto;
import com.netgroup.usecase.statistics.api.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/business")
public class StatisticsController {
    private final StatisticsService service;

    @GetMapping("/statistics")
    public Page<StatisticsDto> getStatisticsFromTo(Authentication auth,
                                                   @RequestParam @DateTimeFormat(
                                                               pattern = "yyyy-mm-DD"
                                                       ) Date from,
                                                   @RequestParam(required = false) @DateTimeFormat(
                                                               pattern = "yyyy-mm-DD"
                                                       ) Optional<Date> possibleTo,
                                                   Pageable pageable) {

        return possibleTo.map(to -> service.getStatisticsFromTo(auth.getName(), from, to, pageable))
                .orElseGet(() -> service.getStatisticsFrom(auth.getName(), from, pageable));
    }

}
