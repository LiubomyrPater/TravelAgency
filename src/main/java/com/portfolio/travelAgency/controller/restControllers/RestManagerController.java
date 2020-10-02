package com.portfolio.travelAgency.controller.restControllers;

import com.portfolio.travelAgency.service.dto.BookingDTO;
import com.portfolio.travelAgency.service.dto.HotelDTO;
import com.portfolio.travelAgency.service.dto.RoomDTO;
import com.portfolio.travelAgency.service.dto.UserDTO;
import com.portfolio.travelAgency.service.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restManagement")
@AllArgsConstructor
public class RestManagerController {

    private final CityService cityService;
    private final HotelService hotelService;
    private final RoomTypeService roomTypeService;
    private final UserService userService;
    private final RoomService roomService;
    private final BookingService bookingService;

    @GetMapping("/getCities")
    public List<String> getCities() {
        return cityService.citiesName();
    }

    @GetMapping("/getHotelsByCity/{city}")
    public List<String> getHotelsByCity(@PathVariable String city) {
        return hotelService.findByCity(city);
    }

    @GetMapping("/getAllHotels")
    public List<HotelDTO> getAllHotelsDTO() {
        return hotelService.findAllDTO();
    }

    @GetMapping("/getTypes/{city},{hotel}")
    public List<String> getRoomTypes(@PathVariable String city,
                                     @PathVariable String hotel) {
        return roomTypeService.findTypesByCityAndHotel(city, hotel);
    }

    @GetMapping("/getAllUsersDTO")
    public List<UserDTO> getAllUsersDTO(){
        return userService.findAllDTO();
    }

    @GetMapping("/getRoomsByTypeHotelCityAvailable_date/{type}, {hotel}, {city}, {arrival}, {departure}, {priceMin}, {priceMax}")
    public List<String> getRoomsName(@PathVariable String type,
                                     @PathVariable String hotel,
                                     @PathVariable String city,
                                     @PathVariable String arrival,
                                     @PathVariable String departure,
                                     @PathVariable Integer priceMin,
                                     @PathVariable Integer priceMax){
        return roomService.findByCityDateHotelType(city, arrival, departure, hotel, type, priceMin, priceMax);
    }

    @GetMapping("/getRoomsDTObyHotelID/{hotel_id}")
    public List<RoomDTO> getRoomsDTObyHotelID(@PathVariable Long hotelID){
        return roomService.findByHotelID(hotelID);
    }

    @GetMapping("/getBookingsDTObyUser")
    public List<BookingDTO> getBookingsDTO(@PathVariable String email){
        return bookingService.findUserBookingsByEmail(email);
    }

    @GetMapping("/getBookingsByRoom")
    public List<BookingDTO> getBookingsByRoom(@PathVariable Long roomID){
        return bookingService.findAllByRoom(roomID);
    }


/*
    @PostMapping("/groups")
    public Group createGroup(@RequestBody Group group) {
        return groupService.create(group);
    }



    @PutMapping("/groups")
    public Group updateGroup(@RequestBody Group group) {
        if (group.getId() == null) {
            throw new BadRequestException("cannot update group without id");
        }
        return groupService.update(group);
    }

    @DeleteMapping("/groups/{id}")
    public void deleteGroup(@PathVariable Integer id) {
        groupService.deleteById(id);
    }
*/






}
