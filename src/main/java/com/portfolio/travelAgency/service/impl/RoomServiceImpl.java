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

import java.time.LocalDate;
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
    public Room findByID(Long id) {
        return roomRepository.findById(id).get();
    }

    @Override
    public List<RoomDTO> findByHotelID(Long hotelID) {
        return roomRepository.findByHotel(hotelService.findByID(hotelID))
                .stream()
                .map(roomMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addRoomToHotel(RoomDTO roomDTO) {
        Room room = roomMapper.toEntity(roomDTO);
        roomRepository.save(room);
    }

    @Override
    public List<String> findByCityDateHotelType(String city,
                                                String arrival,
                                                String departure,
                                                String hotel,
                                                String typeRoom,
                                                Integer priceMin,
                                                Integer priceMax
    ){
        Hotel persistedHotel = hotelService.findByNameAndCity(hotel, city);

        RoomType roomType = roomTypeService.findByNameAndHotelAndCity(typeRoom, hotel, city);

        List<Room> roomsAvailableByDate = persistedHotel.getRooms()
                .stream()
                .filter(x -> x.getType().equals(roomType))
                .filter(r -> bookingService.checkAvailabilityRooms(r.getBookings(), arrival, departure))
                .distinct()
                .collect(Collectors.toList());

        List<Room> roomsAvailableByPrice = new ArrayList<>();

        return roomsAvailableByDate.stream().map(Room::getNumber).collect(Collectors.toList());
    }

    @Override
    public boolean earlyArrival(String city, String arrival, String hotel, String room) {

        return hotelService.findByNameAndCity(hotel, city).getRooms().stream()
                .filter(r -> r.getNumber().equals(room))
                .findFirst()
                .get()
                .getBookings()
                .stream()
                .filter(b -> !b.getDeparture().isBefore(LocalDate.now()))
                .noneMatch(b -> b.getDeparture().isEqual(LocalDate.parse(arrival)));
    }

    @Override
    public boolean lateDeparture(String city, String departure, String hotel, String room) {

        return hotelService.findByNameAndCity(hotel, city).getRooms().stream()
                .filter(r -> r.getNumber().equals(room))
                .findFirst()
                .get()
                .getBookings()
                .stream()
                .filter(b -> !b.getDeparture().isBefore(LocalDate.now()))
                .noneMatch(b -> b.getArrival().isEqual(LocalDate.parse(departure)));
    }

    @Override
    public List<String> roomsName() {
        List<String> roomNumber = new ArrayList<>();
        roomRepository.findAll().forEach(x -> roomNumber.add(x.getNumber()));
        return roomNumber;
    }
}
