package com.portfolio.travelAgency.service.interfaces;

import com.portfolio.travelAgency.entity.Booking;
import com.portfolio.travelAgency.entity.Room;
import com.portfolio.travelAgency.service.dto.BookingDTO;

import java.util.Set;

public interface BookingService {

    void createNewBooking(BookingDTO bookingDTO);

    boolean matchDateArrival(String date);

    boolean matchDateDeparture(String dateArrival, String dateDeparture);

    Set<Booking> findUserBookingsByEmail(String email);

    boolean checkAvailabilityRooms (Set<Booking> bookingSet, String arrival, String departure);
}
