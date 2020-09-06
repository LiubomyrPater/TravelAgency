package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.entity.*;
import com.portfolio.travelAgency.repository.RoomRepository;

import com.portfolio.travelAgency.service.dto.RoomDTO;
import com.portfolio.travelAgency.service.interfaces.BookingService;
import com.portfolio.travelAgency.service.interfaces.HotelService;
import com.portfolio.travelAgency.service.interfaces.RoomService;
import com.portfolio.travelAgency.service.interfaces.RoomTypeService;
import com.portfolio.travelAgency.service.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {



    private final RoomMapper roomMapper;
    private final RoomRepository roomRepository;
    private final RoomTypeService roomTypeService;
    private final HotelService hotelService;
    private final BookingService bookingService;

    @Override
    @Transactional
    public void addRoomToHotel(RoomDTO roomDTO) {
        Room room = roomMapper.toEntity(roomDTO);
        roomRepository.save(room);
    }

    @Override
    public List<Room> findByCityDateHotelType(String city, String arrival, String departure, String hotel, String typeRoom) {

        Hotel persistedHotel = hotelService.findByNameAndCity(hotel, city);
        RoomType roomType = roomTypeService.findByName(typeRoom);

        List<Room> freeRoomsList = new ArrayList<>();

        Set<Room> rooms = persistedHotel.getRooms().stream().filter(x -> x.getType().equals(roomType)).collect(Collectors.toSet());
        for (Room r: rooms) {
            if (bookingService.checkAvailabilityRooms(r.getBookings(), arrival, departure))
                freeRoomsList.add(r);
        }
        return freeRoomsList.stream().distinct().collect(Collectors.toList());
    }


    @Override
    public boolean earlyArrival(String city, String arrival, String departure, String hotel, String typeRoom, String room) {
/*
        Room selectedRoom = findByCityDateHotelType(city, arrival, departure, hotel, typeRoom)
                .stream()
                .filter(x -> x.getNumber().equals(room))
                .findFirst()
                .get();
                */
        return true;
    }


    @Override
    public boolean lateDeparture(String city, String arrival, String departure, String hotel, String typeRoom, String room) {
        return true;
    }

    @Override
    public List<String> roomsName() {
        List<String> roomNumber = new ArrayList<>();
        roomRepository.findAll().forEach(x -> roomNumber.add(x.getNumber()));
        return roomNumber;
    }
}
