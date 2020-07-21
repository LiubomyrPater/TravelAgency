package com.portfolio.travelAgency.service.interfaces;

import com.portfolio.travelAgency.entity.RoomType;

import java.util.List;

public interface RoomTypeService {

    List<RoomType> findRoomTypesAvailable(String city, String arrival, String departure, String hotel);
}
