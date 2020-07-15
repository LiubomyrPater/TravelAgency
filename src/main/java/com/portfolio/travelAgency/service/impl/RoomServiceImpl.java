package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.entity.City;
import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.entity.Room;
import com.portfolio.travelAgency.repository.HotelRepository;
import com.portfolio.travelAgency.repository.RoomRepository;
import com.portfolio.travelAgency.service.dto.HotelDTO;
import com.portfolio.travelAgency.service.dto.RoomDTO;
import com.portfolio.travelAgency.service.interfaces.RoomService;
import com.portfolio.travelAgency.service.mapper.RoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomMapper roomMapper;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

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
}
