package com.portfolio.travelAgency.service.interfaces;

import com.portfolio.travelAgency.entity.Room;
import com.portfolio.travelAgency.service.dto.RoomDTO;

import java.util.List;

public interface RoomService {

    void addRoomToHotel(RoomDTO roomDTO);

    List<Room> findByCityDateHotelType(String city, String arrival, String departure, String hotel, String typeRoom);

    boolean earlyArrival(String city, String arrival, String departure, String hotel, String typeRoom, String room);

    boolean lateDeparture (String city, String arrival, String departure, String hotel, String typeRoom, String room);

    List<String> roomsName();
}
