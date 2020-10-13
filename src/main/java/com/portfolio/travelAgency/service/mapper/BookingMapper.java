package com.portfolio.travelAgency.service.mapper;

import com.portfolio.travelAgency.entity.Booking;
import com.portfolio.travelAgency.repository.CityRepository;
import com.portfolio.travelAgency.repository.HotelRepository;
import com.portfolio.travelAgency.repository.RoomRepository;
import com.portfolio.travelAgency.repository.UserRepository;
import com.portfolio.travelAgency.service.dto.BookingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BookingMapper {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final CityRepository cityRepository;

    public Booking toEntity(BookingDTO bookingDTO){

        Booking result = new Booking();

        result.setArrival(LocalDate.parse(bookingDTO.getArrival()));
        result.setDeparture(LocalDate.parse(bookingDTO.getDeparture()));
        result.setCreateMoment(LocalDateTime.now());
        result.setPrice(bookingDTO.getPrice());
        result.setEarlyArrival(bookingDTO.isEarlyArrival());
        result.setLateDeparture(bookingDTO.isLateDeparture());
        result.setUser(userRepository.findByEmail(bookingDTO.getUser()).get());
        result.setRoom(roomRepository.findByNumberAndHotel(bookingDTO.getRoom(),
                hotelRepository.findByNameAndCity(bookingDTO.getHotel(),
                        cityRepository.findByName(bookingDTO.getCity())
                                .get())
                        .get())
                .get());

        return result;
    }

    public BookingDTO toDTO(Booking booking){

        BookingDTO result = new BookingDTO();

        result.setUser(booking.getUser().getEmail());
        result.setPrice(booking.getPrice());
        result.setArrival(booking.getArrival().toString());
        result.setDeparture(booking.getDeparture().toString());
        result.setEarlyArrival(booking.isEarlyArrival());
        result.setLateDeparture(booking.isLateDeparture());
        result.setCreateMoment(booking.getCreateMoment());
        result.setRoom(booking.getRoom().getNumber());
        result.setTypeRoom(booking.getRoom().getType().getName());
        result.setHotel(booking.getRoom().getHotel().getName());
        result.setCity(cityRepository.findByHotels(booking.getRoom().getHotel()).getName());
        result.setPaid(booking.isPaid());
        result.setId(booking.getId());

        return result;
    }
}
