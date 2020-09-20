package com.portfolio.travelAgency.service.mapper;

import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.repository.CityRepository;
import com.portfolio.travelAgency.service.dto.HotelDTO;
import com.portfolio.travelAgency.service.interfaces.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HotelMapper {

    private final CityService cityService;

    public Hotel toEntity(HotelDTO hotelDTO) {
        Hotel result = new Hotel();
        result.setName(hotelDTO.getName());
        result.setCity(cityService.findByName(hotelDTO.getCity()));
        //result.setRooms(hotelDTO.getRooms());
        return result;
    }

    public HotelDTO toDTO(Hotel hotel) {
        HotelDTO result = new HotelDTO();
        result.setId(hotel.getId());
        result.setCity(hotel.getCity().getName());
        result.setName(hotel.getName());
        //result.setRooms(hotel.getRooms());
        return result;
    }
}
