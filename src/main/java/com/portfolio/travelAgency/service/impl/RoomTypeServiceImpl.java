package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.entity.Room;
import com.portfolio.travelAgency.entity.RoomType;
import com.portfolio.travelAgency.repository.HotelRepository;
import com.portfolio.travelAgency.repository.RoomTypeRepository;
import com.portfolio.travelAgency.service.dto.HotelDTO;
import com.portfolio.travelAgency.service.interfaces.BookingService;
import com.portfolio.travelAgency.service.interfaces.CityService;
import com.portfolio.travelAgency.service.interfaces.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {

    private final HotelRepository hotelRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final CityService cityService;
    private final BookingService bookingService;


    @Override
    public Integer superMinPrice() {
        return roomTypeRepository.findAll()
                .stream()
                .map(RoomType::getPrice)
                .min(Comparator.comparing(Integer::intValue))
                .get();
    }

    @Override
    public Integer superMaxPrice() {
        return roomTypeRepository.findAll()
                .stream()
                .map(RoomType::getPrice)
                .max(Comparator.comparing(Integer::intValue))
                .get();
    }

    @Override
    public Integer findTheCheapestType(String city) {
        List<Long> hotelsID = hotelRepository.findByCity(cityService.findByName(city))
                .stream()
                .map(Hotel::getId)
                .collect(Collectors.toList());

        return roomTypeRepository.findAll()
                .stream()
                .filter(roomType -> hotelsID.contains(roomType.getHotel().getId()))
                .map(RoomType::getPrice)
                .min(Comparator.comparing(Integer::intValue))
                .get();
    }

    @Override
    public Integer findTheMostExpensiveType(String city) {
        List<Long> hotelsID = hotelRepository.findByCity(cityService.findByName(city))
                .stream()
                .map(Hotel::getId)
                .collect(Collectors.toList());

        return roomTypeRepository.findAll()
                .stream()
                .filter(roomType -> hotelsID.contains(roomType.getHotel().getId()))
                .map(RoomType::getPrice)
                .max(Comparator.comparing(Integer::intValue))
                .get();
    }

    @Override
    public List<String> findTypesByCityAndHotel(String city, String hotel) {
        Hotel persistedHotel = hotelRepository.findByNameAndCity(hotel,cityService.findByName(city)).get();
        return roomTypeRepository.findAllByHotel(persistedHotel)
                .stream()
                .map(RoomType::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public RoomType findByNameAndHotelAndCity(String name, String hotel, String city) {
        Hotel persistedHotel = hotelRepository.findByNameAndCity(hotel, cityService.findByName(city)).get();
        return roomTypeRepository.findByNameAndAndHotel(name, persistedHotel)
                .orElseThrow(() -> new EntityNotFoundException("Room type " + name + " was not found"));
    }

    @Override
    public List<String> findRoomTypesAvailable(String city, String arrival, String departure, String hotel) {

        return hotelRepository.findByNameAndCity(hotel, cityService.findByName(city)).get().getRooms()
                .stream()
                .filter(r -> bookingService.checkAvailabilityRooms(r.getBookings(), arrival, departure))
                .map(Room::getType)
                .map(RoomType::getName)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> roomTypesName() {
        return roomTypeRepository.findAll()
                .stream()
                .map(RoomType::getName)
                .collect(Collectors.toList());
    }
}
