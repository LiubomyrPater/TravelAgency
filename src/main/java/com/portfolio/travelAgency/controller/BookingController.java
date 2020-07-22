package com.portfolio.travelAgency.controller;

import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.entity.Room;
import com.portfolio.travelAgency.entity.RoomType;
import com.portfolio.travelAgency.repository.CityRepository;
import com.portfolio.travelAgency.repository.RoomRepository;
import com.portfolio.travelAgency.service.interfaces.BookingService;
import com.portfolio.travelAgency.service.interfaces.HotelService;
import com.portfolio.travelAgency.service.interfaces.RoomService;
import com.portfolio.travelAgency.service.interfaces.RoomTypeService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class BookingController {

    private final BookingService bookingService;
    private final HotelService hotelService;
    private final RoomTypeService roomTypeService;
    private final RoomService roomService;

    public BookingController(BookingService bookingService, HotelService hotelService, RoomTypeService roomTypeService, RoomService roomService) {
        this.bookingService = bookingService;
        this.hotelService = hotelService;
        this.roomTypeService = roomTypeService;
        this.roomService = roomService;
    }


    @GetMapping("/home/dateArrivalSelect")
    @ResponseBody
    public String getArrival(@RequestParam String arrival){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("decide", bookingService.matchDateArrival(arrival));
        return jsonObject.toString();
    }


    @GetMapping("/home/dateDepartureSelect")
    @ResponseBody
    public String getDeparture(@RequestParam String arrival,
                               @RequestParam String departure,
                               @RequestParam String city){

        JSONObject jsonObjectDecide = new JSONObject();
        boolean decide = bookingService.matchDateDeparture(arrival, departure);
        jsonObjectDecide.put("decide", decide);

        if (decide){
            JSONArray jsonArrayHotels = new JSONArray();
            List<Hotel> freeHotels = hotelService.findFreeHotels(city, arrival, departure);
            for (Hotel h: freeHotels) {
                JSONObject jsonObjectHotel = new JSONObject();
                jsonObjectHotel.put("name", h.getName());
                jsonArrayHotels.add(jsonObjectHotel);
            }
            return jsonArrayHotels.toString();
        }else return jsonObjectDecide.toString();
    }


    @GetMapping("/home/hotelSelectForm")
    @ResponseBody
    public String getType(@RequestParam String arrival,
                          @RequestParam String departure,
                          @RequestParam String city,
                          @RequestParam String hotel){

        JSONArray jsonArrayTypes = new JSONArray();
        List<RoomType> roomTypes = roomTypeService.findRoomTypesAvailable(city, arrival, departure, hotel);
        for (RoomType type: roomTypes) {
            JSONObject jsonObjectRoomType = new JSONObject();
            jsonObjectRoomType.put("name", type.getName());
            jsonArrayTypes.add(jsonObjectRoomType);
        }
        return jsonArrayTypes.toString();
    }

    @GetMapping("/home/typeSelectForm")
    @ResponseBody
    public String getType(@RequestParam String arrival,
                          @RequestParam String departure,
                          @RequestParam String city,
                          @RequestParam String hotel,
                          @RequestParam String type){

        List<Room> rooms = roomService.findByCityDateHotelType(city, arrival, departure, hotel, type);

        JSONArray jsonArrayRoom = new JSONArray();
        for (Room r: rooms) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("number", r.getNumber());
            jsonArrayRoom.add(jsonObject);
        }
        return jsonArrayRoom.toString();
    }
}
