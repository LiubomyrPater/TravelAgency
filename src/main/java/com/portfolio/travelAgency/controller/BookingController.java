package com.portfolio.travelAgency.controller;

import com.portfolio.travelAgency.service.interfaces.BookingService;
import com.portfolio.travelAgency.service.interfaces.HotelService;
import com.portfolio.travelAgency.service.interfaces.RoomService;
import com.portfolio.travelAgency.service.interfaces.RoomTypeService;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final HotelService hotelService;
    private final RoomTypeService roomTypeService;
    private final RoomService roomService;

    @GetMapping("home/citySelect")
    @ResponseBody
    public String getPrices(@RequestParam String city){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("minPrice", roomTypeService.findTheCheapestType(city));
        jsonObject.put("maxPrice", roomTypeService.findTheMostExpensiveType(city));

        return jsonObject.toString();
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
            hotelService.findFreeHotels(city, arrival, departure)
                    .forEach(h -> {
                        JSONObject jsonObjectHotel = new JSONObject();
                        jsonObjectHotel.put("name", h);
                        jsonArrayHotels.add(jsonObjectHotel);
                    });
            return jsonArrayHotels.toString();
        }else return jsonObjectDecide.toString();
    }


    @GetMapping("/home/hotelSelectForm")
    @ResponseBody
    public String getHotel(@RequestParam String arrival,
                          @RequestParam String departure,
                          @RequestParam String city,
                          @RequestParam String hotel){

        JSONArray jsonArrayTypes = new JSONArray();

        roomTypeService.findRoomTypesAvailable(city, arrival, departure, hotel)
                .forEach(type -> {
                    JSONObject jsonObjectRoomType = new JSONObject();
                    jsonObjectRoomType.put("name", type);
                    jsonArrayTypes.add(jsonObjectRoomType);
                });
        return jsonArrayTypes.toString();
    }


    @GetMapping("/home/typeSelectForm")
    @ResponseBody
    public String getType(@RequestParam String arrival,
                          @RequestParam String departure,
                          @RequestParam String city,
                          @RequestParam String hotel,
                          @RequestParam String type){

        JSONObject jsonObjectPrice = new JSONObject();
        jsonObjectPrice.put("price", hotelService.getPriceRoomType(hotel,city,type)
        );

        JSONArray jsonArrayRoom = new JSONArray();
        jsonArrayRoom.add(jsonObjectPrice);
        roomService.findByCityDateHotelType(city, arrival, departure, hotel, type)
                .forEach(r -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("number", r);
                    jsonArrayRoom.add(jsonObject);
                });
        return jsonArrayRoom.toString();
    }


    @GetMapping("/home/roomSelectForm")
    @ResponseBody
    public String getRoom(@RequestParam String arrival,
                          @RequestParam String departure,
                          @RequestParam String city,
                          @RequestParam String hotel,
                          //@RequestParam String type,
                          @RequestParam String room){

        JSONArray jsonArrayRoomAvailable = new JSONArray();

        JSONObject earlyArrival = new JSONObject();
        earlyArrival.put("earlyArrival", roomService.earlyArrival(city, arrival, hotel, room));

        JSONObject lateDeparture = new JSONObject();
        lateDeparture.put("lateDeparture", roomService.lateDeparture(city, departure, hotel, room));

        jsonArrayRoomAvailable.add(earlyArrival);
        jsonArrayRoomAvailable.add(lateDeparture);

        return jsonArrayRoomAvailable.toString();
    }
}