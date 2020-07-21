package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.entity.*;
import com.portfolio.travelAgency.repository.CityRepository;
import com.portfolio.travelAgency.repository.HotelRepository;
import com.portfolio.travelAgency.repository.RoomRepository;
import com.portfolio.travelAgency.repository.RoomTypeRepository;
import com.portfolio.travelAgency.service.dto.HotelDTO;
import com.portfolio.travelAgency.service.dto.RoomDTO;
import com.portfolio.travelAgency.service.interfaces.RoomService;
import com.portfolio.travelAgency.service.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
    private final HotelRepository hotelRepository;
    private final CityRepository cityRepository;
    private final RoomTypeRepository roomTypeRepository;

    @Override
    @Transactional
    public void addRoomToHotel(RoomDTO roomDTO) {

        Room room = roomMapper.toEntity(roomDTO);
        roomRepository.save(room);
/*

        Hotel persistedHotel = hotelRepository.findByName(roomDTO.getHotel())
                .orElseThrow(() -> new EntityNotFoundException("Hotel with name " + roomDTO.getHotel() + " was not found"));

        Room persistedRoom = cityRepository.findByName(hotelDTO.getCity())
                .orElseThrow(() -> new EntityNotFoundException("City with name " + hotelDTO.getCity() + " was not found"));

        persistedHotel.getRooms().add(persistedRoom);

        hotelRepository.save(persistedHotel);*/
    }

    @Override
    public List<Room> findByCityDateHotelType(String city, String arrival, String departure, String hotel, String typeRoom) {


        LocalDate arrivalDate = LocalDate.parse(arrival);
        LocalDate departureDate = LocalDate.parse(departure);

        Hotel persistedHotel = hotelRepository.findByNameAndCity(hotel, cityRepository.findByName(city).get()).get();
        RoomType roomType = roomTypeRepository.findByName(typeRoom).get();

        /*Set<Room> rooms = persistedHotel.getRooms().stream().filter(x -> x.getType().equals(roomType)).collect(Collectors.toSet());*/
        Set<Room> rooms = persistedHotel.getRooms();



        List<Room> freeRooms = new ArrayList<>();
        for (Room r: rooms) {
            Set<Booking> bookings = r.getBookings();
            if (bookings.size() == 0){
                freeRooms.add(r);
            }else {
                for (Booking b: bookings) {
                    if ((arrivalDate.isBefore(b.getArrival()) & (departureDate.isBefore(b.getArrival()) || departureDate.isEqual(b.getArrival())))
                            || ((arrivalDate.isEqual(b.getDeparture()) || arrivalDate.isAfter(b.getDeparture())) & departureDate.isAfter(b.getDeparture()))){
                        freeRooms.add(r);
                    }
                }
            }
        }
        return freeRooms.stream().distinct().collect(Collectors.toList());
    }
}
