package com.portfolio.travelAgency.service.mapper;

import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.service.dto.HotelDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HotelMapper {

    public Hotel toEntity(HotelDTO hotelDTO) {
        Hotel result = new Hotel();
        result.setName(hotelDTO.getName());
        result.setRooms(hotelDTO.getRooms());
        return result;
    }

    public HotelDTO toDTO(Hotel hotel) {
        HotelDTO result = new HotelDTO();
        result.setName(hotel.getName());
        result.setRooms(hotel.getRooms());
        return result;
    }
}
