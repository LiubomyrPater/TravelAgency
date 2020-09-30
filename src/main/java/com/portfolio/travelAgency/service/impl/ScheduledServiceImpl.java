package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.service.interfaces.BookingService;
import com.portfolio.travelAgency.service.interfaces.ScheduledService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Slf4j
@Service
@EnableScheduling
@AllArgsConstructor
public class ScheduledServiceImpl implements ScheduledService {

    private final BookingService bookingService;

    @Override
    @Scheduled(cron = "0 0 18 * * *")
    public void runEveryDayAt1800() {
        LocalTime time = LocalTime.now();
        LocalTime targetTime = LocalTime.parse("18:00:00");
        if (!time.isBefore(targetTime)){
            log.info("Start clearing database");
            System.out.println("have moved in archive" + bookingService.archivedTodayBookings());
            log.info("Finish clearing database");
        }
    }

    @Override
    @Scheduled(cron = "0 0 23 * * *")
    public void runEveryDayAt2300() {
        LocalTime time = LocalTime.now();
        LocalTime targetTime = LocalTime.parse("23:00:00");
        if (!time.isBefore(targetTime)) {
            log.info("Start clearing database");
            System.out.println("have moved in archive" + bookingService.archivedUnpaidBookings());
            log.info("Finish clearing database");
        }
    }
}
