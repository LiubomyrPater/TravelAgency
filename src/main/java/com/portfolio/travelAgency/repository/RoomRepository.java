package com.portfolio.travelAgency.repository;

import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {


//    @Query("select u.bookings from User u where u.email=?1")
//    Set<Booking> findUserBookingsByEmail(String email);

    Room findByNumberAndHotel(String number, Hotel hotel);
}
