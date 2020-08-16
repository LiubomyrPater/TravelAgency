package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.entity.*;
import com.portfolio.travelAgency.repository.RoomRepository;

import com.portfolio.travelAgency.service.dto.RoomDTO;
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

    @Override
    @Transactional
    public void addRoomToHotel(RoomDTO roomDTO) {
        Room room = roomMapper.toEntity(roomDTO);
        roomRepository.save(room);
    }

    @Override
    public List<Room> findByCityDateHotelType(String city, String arrival, String departure, String hotel, String typeRoom) {


        LocalDate arrivalDate = LocalDate.parse(arrival);
        LocalDate departureDate = LocalDate.parse(departure);
        LocalDate today = LocalDate.now().minusDays(1);

        Hotel persistedHotel = hotelService.findByNameAndCity(hotel, city);
        RoomType roomType = roomTypeService.findByName(typeRoom);

        Set<Room> rooms = persistedHotel.getRooms().stream().filter(x -> x.getType().equals(roomType)).collect(Collectors.toSet());

        boolean freeRoms = false;

        List<Room> freeRoomsList = new ArrayList<>();
        for (Room r: rooms) {
            Set<Booking> bookings = r.getBookings();
            bookings = bookings.stream().filter(x -> x.getDeparture().isAfter(today)).collect(Collectors.toSet());
            if (bookings.size() == 0){
                freeRoomsList.add(r);
            }else {
                for (Booking b: bookings) {
                    if ((arrivalDate.isBefore(b.getArrival()) & (departureDate.isBefore(b.getArrival()) || departureDate.isEqual(b.getArrival())))
                            || ((arrivalDate.isEqual(b.getDeparture()) || arrivalDate.isAfter(b.getDeparture())) & departureDate.isAfter(b.getDeparture()))){
                        freeRoms = true;
                    }else {
                        freeRoms = false;
                    }
                }
                if (freeRoms){
                    freeRoomsList.add(r);
                }
            }
        }
        return freeRoomsList.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> roomsName() {
        List<String> roomNumber = new ArrayList<>();
        roomRepository.findAll().forEach(x -> roomNumber.add(x.getNumber()));
        return roomNumber;
    }
}
