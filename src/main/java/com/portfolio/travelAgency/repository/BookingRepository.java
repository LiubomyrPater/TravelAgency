package com.portfolio.travelAgency.repository;

import com.portfolio.travelAgency.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Set;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {



    @Query("select u.bookings from User u where u.id=?1")
    Set<Booking> findUserBookings(Long userId);

    @Query("select u.bookings from User u where u.email=?1")
    Set<Booking> findUserBookingsByEmail(String email);
}
