package com.portfolio.travelAgency.controller;

import com.portfolio.travelAgency.entity.Hotel;
import com.portfolio.travelAgency.entity.Room;
import com.portfolio.travelAgency.repository.CityRepository;
import com.portfolio.travelAgency.repository.HotelRepository;
import com.portfolio.travelAgency.repository.RoomRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

@Controller
public class BookingController {

    private final HotelRepository hotelRepository;
    private final CityRepository cityRepository;
    private final RoomRepository roomRepository;


    public BookingController(HotelRepository hotelRepository, CityRepository cityRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.cityRepository = cityRepository;
        this.roomRepository = roomRepository;
    }

    @GetMapping("/home/citySelectForm")
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
    }

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
}
