package com.portfolio.travelAgency.controller;

import com.portfolio.travelAgency.controller.validator.AddHotelValidator;
import com.portfolio.travelAgency.controller.validator.AddRoomValidator;
import com.portfolio.travelAgency.entity.*;
import com.portfolio.travelAgency.repository.*;
import com.portfolio.travelAgency.service.dto.HotelDTO;
import com.portfolio.travelAgency.service.dto.RoomDTO;
import com.portfolio.travelAgency.service.dto.UserDTO;
import com.portfolio.travelAgency.service.interfaces.HotelService;
import com.portfolio.travelAgency.service.interfaces.RoomService;
import com.portfolio.travelAgency.service.mapper.UserMapper;
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
public class ManagerController {

    private final CityRepository cityRepository;
    private final AddHotelValidator addHotelValidator;
    private final HotelService hotelService;
    private final HotelRepository hotelRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final AddRoomValidator addRoomValidator;
    private final RoomService roomService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BookingRepository bookingRepository;


    public ManagerController(CityRepository cityRepository, AddHotelValidator addHotelValidator, HotelService hotelService, HotelRepository hotelRepository, RoomTypeRepository roomTypeRepository, AddRoomValidator addRoomValidator, RoomService roomService, UserRepository userRepository, UserMapper userMapper, BookingRepository bookingRepository) {
        this.cityRepository = cityRepository;
        this.addHotelValidator = addHotelValidator;
        this.hotelService = hotelService;
        this.hotelRepository = hotelRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.addRoomValidator = addRoomValidator;
        this.roomService = roomService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.bookingRepository = bookingRepository;
    }


    @GetMapping("/management")
    public String managementPage() {
        return "management";
    }


    @GetMapping("/management/addHotel")
    public String addHotelToCity(Model model) {
        model.addAttribute("addHotelForm", new HotelDTO());
        //List<City> cities = cityRepository.findAllBy();
        List<City> cities = cityRepository.findAll();
        List<String> cityName = new ArrayList<>();
        cities.forEach(x-> cityName.add(x.getName()));
        model.addAttribute("cities",cityName);

        return "addHotel";
    }

    @PostMapping("/management/addHotel")
    public String addHotelToCity(@ModelAttribute("addHotelForm") HotelDTO hotelDTO,
                               BindingResult bindingResult) {
        addHotelValidator.validate(hotelDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            return "addHotel";
        }
        hotelService.addHotelToCity(hotelDTO);
        return "redirect:/";
    }



    @GetMapping("/management/addRoom")
    public String addRoomToHotel(Model model) {
        model.addAttribute("addRoomForm", new RoomDTO());

        List<City> cities = cityRepository.findAll();
        List<String> cityName = new ArrayList<>();
        cities.forEach(x-> cityName.add(x.getName()));
        model.addAttribute("cities",cityName);

        List<RoomType> roomTypes = roomTypeRepository.findAll();
        List<String> roomTypeName = new ArrayList<>();
        roomTypes.forEach(x -> roomTypeName.add(x.getName()));
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
        List<Hotel> hotels = hotelRepository.findByCity(cityRepository.findByName(city).get());
        for (Hotel h: hotels) {
            JSONObject jsonObjectHotel = new JSONObject();
            jsonObjectHotel.put("name", h.getName());
            jsonArrayHotels.add(jsonObjectHotel);
        }
        return jsonArrayHotels.toString();
    }
}
