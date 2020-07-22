package com.portfolio.travelAgency.controller;

import com.portfolio.travelAgency.controller.validator.BookingValidator;
import com.portfolio.travelAgency.entity.Booking;
import com.portfolio.travelAgency.repository.*;
import com.portfolio.travelAgency.service.dto.BookingDTO;
import com.portfolio.travelAgency.service.impl.BookingServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    private final BookingServiceImpl bookingService;
    private final BookingValidator bookingValidator;
    private final RoleRepository roleRepository;

    public UserController(BookingRepository bookingRepository, CityRepository cityRepository, HotelRepository hotelRepository, RoomRepository roomRepository, RoomTypeRepository roomTypeRepository, UserRepository userRepository, BookingServiceImpl bookingService, BookingValidator bookingValidator, RoleRepository roleRepository) {
        this.bookingRepository = bookingRepository;
        this.cityRepository = cityRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.userRepository = userRepository;
        this.bookingService = bookingService;
        this.bookingValidator = bookingValidator;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/home")
    public String getHomePage(Model model,
                              Principal principal,
                              @ModelAttribute("bookingForm") BookingDTO bookingDTO){

        bookingDTO.setUser(principal.getName());

        List<String> cityName = new ArrayList<>();
        cityRepository.findAll().forEach(x-> cityName.add(x.getName()));
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


    @PostMapping("/home")
    public String reserve(@ModelAttribute ("bookingForm") BookingDTO bookingDTO,
                          Principal principal,
                          BindingResult bindingResult){

        bookingValidator.validate(bookingDTO, bindingResult);
        if (bindingResult.hasErrors())
            return "home";

        bookingService.createNewBooking(bookingDTO);

        if (userRepository.findByEmail(principal.getName())
                .get()
                .getRole()
                .contains(roleRepository.findByName("ROLE_MANAGER"))
                ){
            return "redirect:/management/users";
        }else {
            return "redirect:/home/bookings";
        }
    }



    @GetMapping("/home/bookings")
    public String userBookings (Model model, Principal principal){

        Set<Booking> bookings = bookingRepository.findUserBookingsByEmail(principal.getName());
        model.addAttribute("bookings", bookings);
        return "bookings";
    }
}
