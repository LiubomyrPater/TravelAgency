package com.portfolio.travelAgency.service.interfaces;

import com.portfolio.travelAgency.entity.Room;
import com.portfolio.travelAgency.service.dto.RoomDTO;

import java.util.List;

public interface RoomService {

    void addRoomToHotel(RoomDTO roomDTO);

    List<String> findByCityDateHotelType(String city, String arrival,
                                         String departure, String hotel,
                                         String typeRoom, Integer priceMin,
                                         Integer priceMax);

    List<RoomDTO> findByHotelID(Long hotelID);

    boolean earlyArrival(String city, String arrival, String hotel, String room);

    boolean lateDeparture (String city, String departure, String hotel, String room);

    List<String> roomsName();

    Room findByID(Long id);


}
