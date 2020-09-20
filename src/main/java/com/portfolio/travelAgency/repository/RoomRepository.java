package com.portfolio.travelAgency.repository;

import com.portfolio.travelAgency.entity.City;
import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {


    @Query("select h.rooms from Hotel h where h.name=?1 and h.city=?2")
    List<Room> findRoomByHotelAndCity(String name, City city);

    Optional<Room> findByNumberAndHotel(String number, Hotel hotel);

    List<Room> findByHotel(Hotel hotel);
}
