package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.entity.Room;
import com.portfolio.travelAgency.entity.RoomType;
import com.portfolio.travelAgency.repository.HotelRepository;
import com.portfolio.travelAgency.repository.RoomTypeRepository;
import com.portfolio.travelAgency.service.interfaces.BookingService;
import com.portfolio.travelAgency.service.interfaces.CityService;
import com.portfolio.travelAgency.service.interfaces.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
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
    public List<RoomType> findRoomTypesAvailable(String city, String arrival, String departure, String hotel) {

        Hotel persistedHotel = hotelRepository.findByNameAndCity(hotel, cityService.findByName(city)).get();
        Set<Room> rooms = persistedHotel.getRooms();

        List<RoomType> roomTypes = rooms.stream()
                .filter(r -> bookingService.checkAvailabilityRooms(r.getBookings(), arrival, departure))
                .map(Room::getType).collect(Collectors.toList());

        return roomTypes.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> roomTypesName() {

        List<String> roomTypes = new ArrayList<>();
        roomTypeRepository.findAll().forEach(x -> roomTypes.add(x.getName()));
        return roomTypes;
    }
}
