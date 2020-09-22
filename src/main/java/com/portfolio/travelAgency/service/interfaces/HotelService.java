package com.portfolio.travelAgency.service.interfaces;

import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.service.dto.HotelDTO;

import java.util.List;

public interface HotelService {

    void addHotelToCity(HotelDTO hotelDTO);

    List<String> findFreeHotels(String city, String arrival, String departure);

    List<String> hotelsName();

    Hotel findByNameAndCity(String hotel, String city);

    List<HotelDTO> findAll();

    List<HotelDTO> findAllDTO();

    List<String> findByCity(String city);

    Hotel findByID(Long id);

    Integer getPriceRoomType(String hotel, String city, String type);
}
