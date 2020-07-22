package com.portfolio.travelAgency.service.mapper;

import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.entity.Room;
import com.portfolio.travelAgency.entity.RoomType;
import com.portfolio.travelAgency.repository.CityRepository;
import com.portfolio.travelAgency.repository.HotelRepository;
import com.portfolio.travelAgency.repository.RoomTypeRepository;
import com.portfolio.travelAgency.service.dto.RoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
@RequiredArgsConstructor
public class RoomMapper {

    private final HotelRepository hotelRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final CityRepository cityRepository;

    public Room toEntity(RoomDTO roomDTO) {

        Room result = new Room();

        Hotel persistedHotel = hotelRepository.findByNameAndCity(roomDTO.getHotel(), cityRepository.findByName(roomDTO.getCity()).get())
                .orElseThrow(() -> new EntityNotFoundException("Hotel with name " + roomDTO.getHotel() + " was not found"));
        result.setHotel(persistedHotel);

        RoomType persistedRoomType = roomTypeRepository.findByName(roomDTO.getType())
                .orElseThrow(()-> new EntityNotFoundException("Type" + roomDTO.getType() + " was not found"));
        result.setType(persistedRoomType);

        result.setNumber(roomDTO.getNumber());
        result.setPrice(roomDTO.getPrice());
        result.setBookings(roomDTO.getBookings());

        return result;
    }


    public RoomDTO toDTO(Room room) {

        RoomDTO result = new RoomDTO();

        result.setHotel(room.getHotel().getName());
        result.setNumber(room.getNumber());
        result.setPrice(room.getPrice());
        result.setBookings(room.getBookings());

        return result;
    }
}
