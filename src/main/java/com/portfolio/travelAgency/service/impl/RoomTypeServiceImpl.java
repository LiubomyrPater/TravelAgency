package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.entity.Booking;
import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.entity.Room;
import com.portfolio.travelAgency.entity.RoomType;
import com.portfolio.travelAgency.repository.CityRepository;
import com.portfolio.travelAgency.repository.HotelRepository;
import com.portfolio.travelAgency.service.interfaces.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {

    private final HotelRepository hotelRepository;
    private final CityRepository cityRepository;


    @Override
    public List<RoomType> findRoomTypesAvailable(String city, String arrival, String departure, String hotel) {

        LocalDate arrivalDate = LocalDate.parse(arrival);
        LocalDate departureDate = LocalDate.parse(departure);

        Hotel persistedHotel = hotelRepository.findByNameAndCity(hotel, cityRepository.findByName(city).get()).get();
        Set<Room> rooms = persistedHotel.getRooms();

        List<RoomType> roomTypes = new ArrayList<>();
        for (Room r: rooms) {
            Set<Booking> bookings = r.getBookings();
            if (bookings.size() == 0){
                roomTypes.add(r.getType());
            }else {
                for (Booking b: bookings) {
                    if ((arrivalDate.isBefore(b.getArrival()) & (departureDate.isBefore(b.getArrival()) || departureDate.isEqual(b.getArrival())))
                            || ((arrivalDate.isEqual(b.getDeparture()) || arrivalDate.isAfter(b.getDeparture())) & departureDate.isAfter(b.getDeparture()))){
                        roomTypes.add(r.getType());
                    }
                }
            }
        }
        return roomTypes.stream().distinct().collect(Collectors.toList());
    }
}
