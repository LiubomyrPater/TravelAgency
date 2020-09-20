package com.portfolio.travelAgency.controller;

import com.portfolio.travelAgency.controller.validator.BookingValidator;
import com.portfolio.travelAgency.entity.Booking;
import com.portfolio.travelAgency.repository.*;
import com.portfolio.travelAgency.service.dto.BookingDTO;
import com.portfolio.travelAgency.service.impl.BookingServiceImpl;
import com.portfolio.travelAgency.service.interfaces.*;
import com.portfolio.travelAgency.service.mapper.BookingMapper;
import lombok.AllArgsConstructor;
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
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class UserController {

    private final BookingValidator bookingValidator;
    private final RoleRepository roleRepository;
    private final RoomTypeService roomTypeService;
    private final RoomService roomService;
    private final HotelService hotelService;
    private final BookingServiceImpl bookingService;
    private final BookingMapper bookingMapper;
    private final CityService cityService;
    private final UserService userService;

    @GetMapping("/home")
    public String getHomePage(Model model,
                              Principal principal,
                              @ModelAttribute("bookingForm") BookingDTO bookingDTO){

        bookingDTO.setUser(principal.getName());

        List<String> cityName = cityService.citiesName();
        model.addAttribute("cities", cityName);

        List<String> hotelName = hotelService.hotelsName();
        model.addAttribute("hotels", hotelName);

        List<String> roomNumber = roomService.roomsName();
        model.addAttribute("rooms", roomNumber);

        List<String> roomTypes = roomTypeService.roomTypesName();
        model.addAttribute("roomTypes", roomTypes);

        List<String> users = userService.usersName();
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

        System.out.println(bookingDTO.getPrice());
        bookingService.createNewBooking(bookingDTO);

        return userService.findByEmail(principal.getName())
                .getRole()
                .contains(roleRepository.findByName("ROLE_MANAGER")) ? "redirect:/management/users" : "redirect:/home/bookings";
    }


    @GetMapping("/home/bookings")
    public String userBookings (Model model, Principal principal){

        List<BookingDTO> bookings = bookingService.findUserBookingsByEmail(principal.getName());
        model.addAttribute("bookings", bookings);
        return "bookings";
    }
}
