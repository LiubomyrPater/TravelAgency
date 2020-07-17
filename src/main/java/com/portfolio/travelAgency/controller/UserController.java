package com.portfolio.travelAgency.controller;

import com.portfolio.travelAgency.entity.Booking;
import com.portfolio.travelAgency.entity.City;
import com.portfolio.travelAgency.repository.*;
import com.portfolio.travelAgency.service.dto.BookingDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private final BookingRepository bookingRepository;
    private final CityRepository cityRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final UserRepository userRepository;

    public UserController(BookingRepository bookingRepository, CityRepository cityRepository, HotelRepository hotelRepository, RoomRepository roomRepository, RoomTypeRepository roomTypeRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.cityRepository = cityRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public String getHomePage(Model model){

        //model.addAttribute("username",principal.getName());

        model.addAttribute("bookingForm", new BookingDTO());
        List<City> cities = cityRepository.findAll();
        List<String> cityName = new ArrayList<>();
        cities.forEach(x-> cityName.add(x.getName()));
        model.addAttribute("cities",cityName);


        List<String> hotelName = new ArrayList<>();
        hotelRepository.findAll().forEach(x -> hotelName.add(x.getName()));
        model.addAttribute("hotels", hotelName);

        List<String> roomNumber = new ArrayList<>();
        roomRepository.findAll().forEach(x -> roomNumber.add(x.getNumber()));
        model.addAttribute("rooms", roomNumber);

        List<String> roomTypes = new ArrayList<>();
        roomTypeRepository.findAll().forEach(x -> roomTypes.add(x.getName()));
        model.addAttribute("roomTypes", roomTypes);

        List<String> users = new ArrayList<>();
        userRepository.findAll().forEach(x -> users.add(x.getEmail()));
        model.addAttribute("users", users);


        return "home";
    }

    @GetMapping("/home/bookings")
    public String userBookings (Model model,
                                Principal principal){
        Set<Booking> bookings = bookingRepository.findUserBookingsByEmail(principal.getName());
        model.addAttribute("bookings", bookings);
        return "bookings";
    }
}
