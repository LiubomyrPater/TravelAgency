package com.portfolio.travelAgency.service.interfaces;

import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.service.dto.HotelDTO;

import java.util.List;

public interface HotelService {

    void addHotelToCity(HotelDTO hotelDTO);

    List<Hotel> findFreeHotels(String city, String arrival, String departure);
}
