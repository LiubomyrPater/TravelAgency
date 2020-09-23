package com.portfolio.travelAgency.controller;

import com.portfolio.travelAgency.controller.validator.AddHotelValidator;
import com.portfolio.travelAgency.controller.validator.AddRoomValidator;
import com.portfolio.travelAgency.service.dto.BookingDTO;
import com.portfolio.travelAgency.service.dto.HotelDTO;
import com.portfolio.travelAgency.service.dto.RoomDTO;
import com.portfolio.travelAgency.service.dto.UserDTO;
import com.portfolio.travelAgency.service.interfaces.*;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class ManagerController {

    private final AddHotelValidator addHotelValidator;
    private final AddRoomValidator addRoomValidator;

    private final HotelService hotelService;
    private final RoomService roomService;
    private final CityService cityService;
    private final UserService userService;
    private final BookingService bookingService;
    private final RoomTypeService roomTypeService;

    @GetMapping("/management")
    public String managementPage() {
        /*System.out.println(bookingService.archivedOldBookings());*/
        return "management";
    }

    @GetMapping("/restManagement")
    public String restManagementPage() {
        return "restManagement";
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
                                 BindingResult bindingResult,
                                 Model model) {
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

        List<UserDTO> userDTOS = userService.findAllDTO();
        model.addAttribute("users", userDTOS);
        return "users";
    }

    @GetMapping("/management/hotels")
    public String manageHotels (Model model){

        List<HotelDTO> hotelDTOS = hotelService.findAll();
        model.addAttribute("hotels", hotelDTOS);
        return "hotels";
    }

    @GetMapping("/management/bookings")
    public String userBookings (@RequestParam("userId") Long userId,
                                Model model){
        List<BookingDTO> bookings = bookingService.findUserBookingByID(userId);
        model.addAttribute("bookings", bookings);
        return "bookings";
    }

    @GetMapping("/management/citySelectForm")
    @ResponseBody
    public String getHotelsInCity(@RequestParam String city){

        JSONArray jsonArrayHotels = new JSONArray();

        hotelService.findByCity(city)
        .forEach(h -> {
            JSONObject jsonObjectHotel = new JSONObject();
            jsonObjectHotel.put("name", h);
            jsonArrayHotels.add(jsonObjectHotel);
        });
        return jsonArrayHotels.toString();
    }

    @GetMapping("/management/hotelSelectForm")
    @ResponseBody
    public String getTypesInHotel(@RequestParam String hotel,
                                  @RequestParam String city){

        JSONArray jsonArrayHotels = new JSONArray();

        roomTypeService.findTypesByCityAndHotel(city, hotel)
                .forEach(r -> {
                    JSONObject jsonObjectHotel = new JSONObject();
                    jsonObjectHotel.put("name", r);
                    jsonArrayHotels.add(jsonObjectHotel);
                });
        return jsonArrayHotels.toString();
    }
}
