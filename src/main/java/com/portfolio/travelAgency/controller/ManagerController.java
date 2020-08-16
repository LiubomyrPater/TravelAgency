package com.portfolio.travelAgency.controller;

import com.portfolio.travelAgency.controller.validator.AddHotelValidator;
import com.portfolio.travelAgency.controller.validator.AddRoomValidator;
import com.portfolio.travelAgency.entity.*;
import com.portfolio.travelAgency.repository.*;
import com.portfolio.travelAgency.service.dto.HotelDTO;
import com.portfolio.travelAgency.service.dto.RoomDTO;
import com.portfolio.travelAgency.service.dto.UserDTO;
import com.portfolio.travelAgency.service.interfaces.CityService;
import com.portfolio.travelAgency.service.interfaces.HotelService;
import com.portfolio.travelAgency.service.interfaces.RoomService;
import com.portfolio.travelAgency.service.interfaces.RoomTypeService;
import com.portfolio.travelAgency.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller
@AllArgsConstructor
public class ManagerController {

    private final AddHotelValidator addHotelValidator;
    private final AddRoomValidator addRoomValidator;

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;

    private final HotelService hotelService;
    private final RoomTypeService roomTypeService;
    private final RoomService roomService;
    private final UserMapper userMapper;
    private final CityService cityService;

    @GetMapping("/management")
    public String managementPage() {
        return "management";
    }


    @GetMapping("/management/addHotel")
    public String addHotelToCity(Model model) {
        model.addAttribute("addHotelForm", new HotelDTO());
        List<String> cityName = cityService.citiesName();
        model.addAttribute("cities",cityName);
        return "addHotel";
    }

    @PostMapping("/management/addHotel")
    public String addHotelToCity(@ModelAttribute("addHotelForm") HotelDTO hotelDTO,
                               BindingResult bindingResult, Model model) {
        addHotelValidator.validate(hotelDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            List<String> cityName = cityService.citiesName();
            model.addAttribute("cities",cityName);
            return "addHotel";
        }
        hotelService.addHotelToCity(hotelDTO);
        return "redirect:/";
    }


    @GetMapping("/management/addRoom")
    public String addRoomToHotel(Model model) {

        model.addAttribute("addRoomForm", new RoomDTO());

        List<String> cityName = cityService.citiesName();
        model.addAttribute("cities",cityName);

        List<String> roomTypeName = roomTypeService.roomTypesName();
        model.addAttribute("types", roomTypeName);

        return "addRoom";
    }

    @PostMapping("/management/addRoom")
    public String addRoomToHotel(@ModelAttribute("addRoomForm") RoomDTO roomDTO,
                               BindingResult bindingResult){
        addRoomValidator.validate(roomDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "addRoom";
        }
        roomService.addRoomToHotel(roomDTO);
        return "addRoom";
    }

    @GetMapping("/management/users")
    public String allUsers (Model model){

        List<UserDTO> userDTOS = new ArrayList<>();
        userRepository.findAll().forEach(x -> userDTOS.add(userMapper.toDTO(x)));
        model.addAttribute("users", userDTOS);
        return "users";
    }

    @GetMapping("/management/bookings")
    public String userBookings (@RequestParam("userId") Long userId,
            Model model){
        Set<Booking> bookings = bookingRepository.findUserBookings(userId);

        model.addAttribute("bookings", bookings);
        return "bookings";
    }

    @GetMapping("/management/citySelectForm")
    @ResponseBody
    public String getHotelsInCity(@RequestParam String city){

        JSONArray jsonArrayHotels = new JSONArray();
        List<Hotel> hotels = hotelRepository.findByCity(cityService.findByName(city));
        for (Hotel h: hotels) {
            JSONObject jsonObjectHotel = new JSONObject();
            jsonObjectHotel.put("name", h.getName());
            jsonArrayHotels.add(jsonObjectHotel);
        }
        return jsonArrayHotels.toString();
    }
}
