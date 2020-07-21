package com.portfolio.travelAgency.controller;

import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.entity.Room;
import com.portfolio.travelAgency.repository.CityRepository;
import com.portfolio.travelAgency.repository.HotelRepository;
import com.portfolio.travelAgency.repository.RoomRepository;
import com.portfolio.travelAgency.service.interfaces.BookingService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BookingController {

    private final HotelRepository hotelRepository;
    private final CityRepository cityRepository;
    private final RoomRepository roomRepository;
    private final BookingService bookingService;



    public BookingController(HotelRepository hotelRepository, CityRepository cityRepository, RoomRepository roomRepository, BookingService bookingService) {
        this.hotelRepository = hotelRepository;
        this.cityRepository = cityRepository;
        this.roomRepository = roomRepository;

        this.bookingService = bookingService;
    }

/*    @GetMapping("/home/citySelectForm")
    @ResponseBody
    public String getHotels(@RequestParam String city) {
        JSONArray jsonArray = new JSONArray();
        List<Hotel> hotels = hotelRepository.findByCity(cityRepository.findByName(city).get());
        for (Hotel h: hotels) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", h.getName());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }*/

    @GetMapping("/home/hotelSelectForm")
    @ResponseBody
    public String getRooms(@RequestParam String hotel,
                           @RequestParam String city) {
        JSONArray jsonArray = new JSONArray();
        List<Room> rooms = roomRepository.findRoomByHotelAndCity(hotel, cityRepository.findByName(city).get());
        for (Room r: rooms) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("number", r.getNumber());
            jsonArray.add(jsonObject);
        }
        System.out.println(jsonArray.toString());
        return jsonArray.toString();
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
            List<Hotel> hotels = hotelRepository.findByCity(cityRepository.findByName(city).get());
            for (Hotel h: hotels) {
                JSONObject jsonObjectHotel = new JSONObject();
                jsonObjectHotel.put("name", h.getName());
                jsonArrayHotels.add(jsonObjectHotel);
            }

            return jsonArrayHotels.toString();
        }else {
            return jsonObjectDecide.toString();
        }
    }
}
