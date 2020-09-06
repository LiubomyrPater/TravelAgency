package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.entity.Booking;
import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.entity.Room;
import com.portfolio.travelAgency.entity.RoomType;
import com.portfolio.travelAgency.repository.HotelRepository;
import com.portfolio.travelAgency.repository.RoomTypeRepository;
import com.portfolio.travelAgency.service.interfaces.CityService;
import com.portfolio.travelAgency.service.interfaces.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
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

    @Override
    public RoomType findByName(String name) {
        return roomTypeRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Room type " + name + " was not found"));
    }

    @Override
    public List<RoomType> findRoomTypesAvailable(String city, String arrival, String departure, String hotel) {

        LocalDate arrivalDate = LocalDate.parse(arrival);
        LocalDate departureDate = LocalDate.parse(departure);
        LocalDate today = LocalDate.now().minusDays(1);

        Hotel persistedHotel = hotelRepository.findByNameAndCity(hotel, cityService.findByName(city)).get();
        Set<Room> rooms = persistedHotel.getRooms();

        boolean freeRoom = false;

        List<RoomType> roomTypes = new ArrayList<>();

        for (Room r: rooms) {
            Set<Booking> bookings = r.getBookings();
            bookings = bookings.stream().filter(x -> x.getDeparture().isAfter(today)).collect(Collectors.toSet());
            if (bookings.size() == 0){
                roomTypes.add(r.getType());
            }else {
                for (Booking b: bookings) {
                    freeRoom = (departureDate.isBefore(b.getArrival()) || (departureDate.isEqual(b.getArrival()) & !b.isEarlyArrival()))
                            || ((arrivalDate.isEqual(b.getDeparture()) & !b.isLateDeparture()) || arrivalDate.isAfter(b.getDeparture())) & departureDate.isAfter(b.getDeparture());
                }
                if (freeRoom)
                    roomTypes.add(r.getType());
            }
        }
        return roomTypes.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> roomTypesName() {

        List<String> roomTypes = new ArrayList<>();
        roomTypeRepository.findAll().forEach(x -> roomTypes.add(x.getName()));
        return roomTypes;
    }
}
