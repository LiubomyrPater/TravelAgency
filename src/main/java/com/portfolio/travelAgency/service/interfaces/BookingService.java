package com.portfolio.travelAgency.service.interfaces;

import com.portfolio.travelAgency.entity.Booking;
import com.portfolio.travelAgency.entity.Room;
import com.portfolio.travelAgency.service.dto.BookingDTO;

import java.awt.*;
import java.util.List;
import java.util.Set;

public interface BookingService {

    void createNewBooking(BookingDTO bookingDTO);

    boolean matchDateArrival(String date);

    boolean matchDateDeparture(String dateArrival, String dateDeparture);

    List<BookingDTO> findUserBookingsByEmail(String email);

    boolean checkAvailabilityRooms (Set<Booking> bookingSet, String arrival, String departure);

    Set<Booking> findAllByRoom(Long roomID);
}
