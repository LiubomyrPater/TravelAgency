package com.portfolio.travelAgency.service.mapper;

import com.portfolio.travelAgency.entity.Booking;
import com.portfolio.travelAgency.entity.BookingsArchived;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ArchivedBookingMapper {

    public BookingsArchived toArchived(Booking booking){
        BookingsArchived archived = new BookingsArchived();
        archived.setOldId(booking.getId());
        archived.setCreateMoment(booking.getCreateMoment());
        archived.setArrival(booking.getArrival());
        archived.setDeparture(booking.getDeparture());
        archived.setEarlyArrival(booking.isEarlyArrival());
        archived.setLateDeparture(booking.isLateDeparture());
        archived.setPrice(booking.getPrice());
        archived.setRoom(booking.getRoom().getId());
        archived.setPaid(booking.isPaid());
        archived.setUser(booking.getUser().getEmail());
        archived.setDeleteMoment(LocalDateTime.now());

        return archived;
    }
}
