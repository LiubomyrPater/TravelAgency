package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.entity.City;
import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.repository.CityRepository;
import com.portfolio.travelAgency.repository.HotelRepository;
import com.portfolio.travelAgency.service.dto.HotelDTO;
import com.portfolio.travelAgency.service.interfaces.HotelService;
import com.portfolio.travelAgency.service.mapper.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelMapper hotelMapper;
    private final HotelRepository hotelRepository;
    private final CityRepository cityRepository;

    @Override
    @Transactional
    public void addHotelToCity(HotelDTO hotelDTO) {

        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        hotelRepository.save(hotel);

        Hotel persistedHotel = hotelRepository.findByName(hotelDTO.getName())
                .orElseThrow(() -> new EntityNotFoundException("Hotel with name " + hotelDTO.getName() + " was not found"));

        City persistedCity = cityRepository.findByName(hotelDTO.getCity())
                .orElseThrow(() -> new EntityNotFoundException("City with name " + hotelDTO.getCity() + " was not found"));

        persistedCity.getHotels().add(persistedHotel);


        cityRepository.save(persistedCity);
    }
}
