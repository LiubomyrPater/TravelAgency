package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.entity.Booking;
import com.portfolio.travelAgency.entity.Room;
import com.portfolio.travelAgency.repository.BookingRepository;
import com.portfolio.travelAgency.service.dto.BookingDTO;
import com.portfolio.travelAgency.service.interfaces.BookingService;
import com.portfolio.travelAgency.service.mapper.BookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    public boolean checkAvailabilityRooms(Set<Booking> bookingSet , String arrival, String departure) {

        LocalDate arrivalDate = LocalDate.parse(arrival);
        LocalDate departureDate = LocalDate.parse(departure);
        LocalDate today = LocalDate.now().minusDays(1);

        bookingSet = bookingSet.stream().filter(x -> x.getDeparture().isAfter(today)).collect(Collectors.toSet());
        if (bookingSet.size() == 0){
            return  true;
        }else {
            long size = bookingSet.stream()
                    .filter(x -> ((arrivalDate.isEqual(x.getDeparture()) && x.isLateDeparture()) || !arrivalDate.isAfter(x.getDeparture())))
                    .filter(x -> (!departureDate.isBefore(x.getArrival()) || (departureDate.isEqual(x.getArrival()) && x.isEarlyArrival())))
                    .count();

            return (size == 0);
        }
    }

    @Override
    public Set<Booking> findUserBookingsByEmail(String email) {
        return bookingRepository.findUserBookingsByEmail(email);
    }

    @Override
    public void createNewBooking(BookingDTO bookingDTO) {
        bookingRepository.save(bookingMapper.toEntity(bookingDTO));
    }

    @Override
    public boolean matchDateArrival(String date) {
        LocalDate choseDate = LocalDate.parse(date);

        return !choseDate.isBefore(LocalDate.now());
    }

    @Override
    public boolean matchDateDeparture(String dateArrival, String dateDeparture) {

        LocalDate arrival = LocalDate.parse(dateArrival);
        LocalDate departure = LocalDate.parse(dateDeparture);

        return !departure.isBefore(arrival) && !departure.isBefore(LocalDate.now())
                && !departure.isEqual(arrival) && !departure.isEqual(LocalDate.now());
    }
}
