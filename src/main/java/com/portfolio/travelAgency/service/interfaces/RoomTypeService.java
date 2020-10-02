package com.portfolio.travelAgency.service.interfaces;

import com.portfolio.travelAgency.entity.RoomType;

import java.util.List;

public interface RoomTypeService {

    List<String> findRoomTypesAvailable(String city, String arrival, String departure, String hotel, Integer priceMin, Integer priceMax);

    List<String> roomTypesName();

    RoomType findByNameAndHotelAndCity(String name, String hotel, String city);

    List<String> findTypesByCityAndHotel(String city, String hotel);

    Integer findTheCheapestType(String city);

    Integer findTheMostExpensiveType(String city);

    Integer superMinPrice();

    Integer superMaxPrice();
}

