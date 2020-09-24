package com.portfolio.travelAgency;

import com.portfolio.travelAgency.service.interfaces.BookingService;
import com.portfolio.travelAgency.service.interfaces.ScheduledService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class TravelAgencyApplication implements CommandLineRunner {

    private final ScheduledService scheduledService;
    private final BookingService bookingService;

    public TravelAgencyApplication(ScheduledService scheduledService, BookingService bookingService) {
        this.scheduledService = scheduledService;
        this.bookingService = bookingService;
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("Start clearing database");
        System.out.println(bookingService.archivedOldBookings());
        log.info("Finish clearing database");

        scheduledService.runEveryDayAt1800();
        scheduledService.runEveryDayAt2300();
    }

    public static void main(String[] args) {
		SpringApplication.run(TravelAgencyApplication.class, args);
	}

}
