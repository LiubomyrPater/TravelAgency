package com.portfolio.travelAgency.service.interfaces;

import com.portfolio.travelAgency.entity.RoomType;

import java.util.List;

public interface RoomTypeService {

    List<RoomType> findRoomTypesAvailable(String city, String arrival, String departure, String hotel);

    List<String> roomTypesName();

    RoomType findByNameAndHotelAndCity(String name, String hotel, String city);

    List<String> findTypesByCityAndHotel(String city, String hotel);
}
