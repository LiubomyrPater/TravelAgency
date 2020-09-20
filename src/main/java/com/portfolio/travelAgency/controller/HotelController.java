package com.portfolio.travelAgency.controller;

import com.portfolio.travelAgency.service.dto.BookingDTO;
import com.portfolio.travelAgency.service.dto.RoomDTO;
import com.portfolio.travelAgency.service.interfaces.BookingService;
import com.portfolio.travelAgency.service.interfaces.RoomService;
import com.portfolio.travelAgency.service.mapper.BookingMapper;
import com.portfolio.travelAgency.service.mapper.RoomMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class HotelController {

    private final RoomService roomService;
    private final RoomMapper roomMapper;
    private final BookingService bookingService;
    private final BookingMapper bookingMapper;


    @GetMapping("/management/hotels/rooms")
    public String getRooms (Model model,
                            @RequestParam("hotelId") Long hotelId){

        List<RoomDTO> roomDTOS = roomService.findByHotelID(hotelId).stream()
                .map(roomMapper::toDTO)
                .collect(Collectors.toList());

        model.addAttribute("rooms", roomDTOS);
        return "rooms";
    }


    @GetMapping("/management/hotels/rooms/roomBookings")
    public String getBookings (Model model,
                               @RequestParam("roomId") Long roomId){

        Set<BookingDTO> bookingDTOS = bookingService.findAllByRoom(roomId).stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toSet());

        model.addAttribute("bookings", bookingDTOS);

        return "roomBookings";
    }
}
