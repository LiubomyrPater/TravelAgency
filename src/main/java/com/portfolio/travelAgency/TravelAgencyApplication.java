package com.portfolio.travelAgency;

import com.portfolio.travelAgency.service.interfaces.BookingService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class TravelAgencyApplication implements CommandLineRunner {

    private final BookingService bookingService;


    public TravelAgencyApplication(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Start clearing database");
        System.out.println(bookingService.archivedOldBookings());
        System.out.println(bookingService.archivedUnpaidBookings());
        log.info("Finish clearing database");
    }

    public static void main(String[] args) {
		SpringApplication.run(TravelAgencyApplication.class, args);
	}

}
