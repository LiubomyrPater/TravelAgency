package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.entity.Booking;
import com.portfolio.travelAgency.entity.City;
import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.entity.Room;
import com.portfolio.travelAgency.repository.CityRepository;
import com.portfolio.travelAgency.repository.HotelRepository;
import com.portfolio.travelAgency.service.dto.HotelDTO;
import com.portfolio.travelAgency.service.interfaces.HotelService;
import com.portfolio.travelAgency.service.mapper.HotelMapper;
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
public class HotelServiceImpl implements HotelService {

    private final HotelMapper hotelMapper;
    private final HotelRepository hotelRepository;
    private final CityRepository cityRepository;

    @Override
    @Transactional
    public void addHotelToCity(HotelDTO hotelDTO) {
        Hotel hotel = hotelMapper.toEntity(hotelDTO);
        hotelRepository.save(hotel);
        Hotel persistedHotel = hotelRepository.findByName(hotelDTO.getName())
                .orElseThrow(() -> new EntityNotFoundException("Hotel with name " + hotelDTO.getName() + " was not found"));

        City persistedCity = cityRepository.findByName(hotelDTO.getCity())
                .orElseThrow(() -> new EntityNotFoundException("City with name " + hotelDTO.getCity() + " was not found"));

        persistedCity.getHotels().add(persistedHotel);
        cityRepository.save(persistedCity);
    }

    @Override
    public List<Hotel> findFreeHotels(String city, String arrival, String departure) {

        LocalDate arrivalDate = LocalDate.parse(arrival);
        LocalDate departureDate = LocalDate.parse(departure);
        LocalDate today = LocalDate.now().minusDays(1);


        List<Hotel> allHotelsByCity = hotelRepository.findByCity(cityRepository.findByName(city).get());

        List<Hotel> freeHotels = new ArrayList<>();

        boolean freeRoms = false;

        for (Hotel h: allHotelsByCity) {
            Set<Room> rooms = h.getRooms();
            for (Room r: rooms) {
                Set<Booking> bookings = r.getBookings();
                bookings = bookings.stream().filter(x -> x.getDeparture().isAfter(today)).collect(Collectors.toSet());
                if (bookings.size() == 0){
                    freeHotels.add(h);
                }else {
                    for (Booking b: bookings) {
                        if ((arrivalDate.isBefore(b.getArrival()) & (departureDate.isBefore(b.getArrival()) || departureDate.isEqual(b.getArrival())))
                                || ((arrivalDate.isEqual(b.getDeparture()) || arrivalDate.isAfter(b.getDeparture())) & departureDate.isAfter(b.getDeparture()))){
                            freeRoms = true;
                        }else
                            freeRoms = false;
                    }
                    if (freeRoms){
                        freeHotels.add(h);
                    }
                }
            }
        }
        return freeHotels.stream().distinct().collect(Collectors.toList());
    }
}
