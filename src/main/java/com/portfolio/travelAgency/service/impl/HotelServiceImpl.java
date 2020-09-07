package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.entity.City;
import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.repository.HotelRepository;
import com.portfolio.travelAgency.service.dto.HotelDTO;
import com.portfolio.travelAgency.service.interfaces.BookingService;
import com.portfolio.travelAgency.service.interfaces.CityService;
import com.portfolio.travelAgency.service.interfaces.HotelService;
import com.portfolio.travelAgency.service.mapper.HotelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {


    private final HotelMapper hotelMapper;
    private final HotelRepository hotelRepository;
    private final CityService cityService;
    private final BookingService bookingService;

    @Override
    public Hotel findByNameAndCity(String hotel, String city) {
        return hotelRepository.findByNameAndCity(hotel, cityService.findByName(city))
                .orElseThrow(() -> new EntityNotFoundException("Hotel was not found"));
    }

    @Override
    public Hotel findByName(String name) {
        return hotelRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Hotel with name " + name + " was not found"));
    }

    @Override
    @Transactional
    public void addHotelToCity(HotelDTO hotelDTO) {
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        hotelRepository.save(hotel);

        Hotel persistedHotel = findByName(hotelDTO.getName());

        City persistedCity = cityService.findByName(hotelDTO.getCity());

        persistedCity.getHotels().add(persistedHotel);
        cityService.save(persistedCity);
    }

    @Override
    public List<Hotel> findFreeHotels(String city, String arrival, String departure) {

        List<Hotel> allHotelsByCity = hotelRepository.findByCity(cityService.findByName(city));

        List<Hotel> freeHotels = new ArrayList<>();

        allHotelsByCity.forEach(h -> h.getRooms().stream()
                .filter(r -> bookingService.checkAvailabilityRooms(r.getBookings(), arrival, departure))
                .map(r -> h)
                .forEach(freeHotels::add));

        return freeHotels.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> hotelsName() {
        List<String> hotelName = new ArrayList<>();
        hotelRepository.findAll().forEach(x -> hotelName.add(x.getName()));
        return hotelName;
    }
}
