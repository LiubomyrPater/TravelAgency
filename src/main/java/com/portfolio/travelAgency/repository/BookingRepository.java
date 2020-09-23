package com.portfolio.travelAgency.repository;

import com.portfolio.travelAgency.entity.Booking;
import com.portfolio.travelAgency.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select u.bookings from User u where u.id=?1")
    List<Booking> findUserBookings(Long userId);

    @Query("select u.bookings from User u where u.email=?1")
    List<Booking> findUserBookingsByEmail(String email);

    Set<Booking> findByRoom(Room room);

    List<Booking> findAllByDepartureBefore(LocalDate now);

    List<Booking> findAllByArrivalBefore(LocalDate now);
}
