package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.entity.Room;
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
    public Integer getPriceRoomType(String hotel, String city, String type) {
        return findByNameAndCity(hotel, city).getRoomTypes()
                .stream()
                .filter(x -> x.getName().equals(type))
                .findFirst()
                .get()
                .getPrice();
    }

    @Override
    public List<String> findByCity(String city) {
        return hotelRepository.findByCity(cityService.findByName(city))
                .stream()
                .map(Hotel::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Hotel findByID(Long id) {
        return hotelRepository.findById(id).get();
    }

    @Override
    public List<HotelDTO> findAllDTO() {
        return hotelRepository.findAll()
                .stream()
                .map(hotelMapper::toDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<HotelDTO> findAll() {
        return hotelRepository.findAll()
                .stream()
                .map(hotelMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Hotel findByNameAndCity(String hotel, String city) {
        return hotelRepository.findByNameAndCity(hotel, cityService.findByName(city))
                .orElseThrow(() -> new EntityNotFoundException("Hotel was not found"));
    }

    @Override
    @Transactional
    public void addHotelToCity(HotelDTO hotelDTO) {
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        hotelRepository.save(hotel);
    }

    @Override
    public List<String> findFreeHotels(String city, String arrival, String departure, int priceMin, int priceMax) {

        List<Hotel> freeHotels = new ArrayList<>();
        hotelRepository.findByCity(cityService.findByName(city))
                .forEach(h -> h.getRooms()
                        .stream()
                        .filter(r -> bookingService.checkAvailabilityRooms(r.getBookings(), arrival, departure))
                        .map(r -> h)
                        .distinct()
                        .forEach(freeHotels::add));


        List<Hotel> availableByPrice = freeHotels.stream()
                .filter(hotel -> hotel.getRooms()
                        .stream()
                        .anyMatch(room -> priceMin <= room.getType().getPrice() && room.getType().getPrice() <= priceMax))
                .collect(Collectors.toList());

        return availableByPrice.stream().map(Hotel::getName).collect(Collectors.toList());
    }

    @Override
    public List<String> hotelsName() {
        return hotelRepository.findAll()
                .stream()
                .map(Hotel::getName)
                .collect(Collectors.toList());
    }
}
