package com.portfolio.travelAgency.controller;

import com.portfolio.travelAgency.service.dto.BookingDTO;
import com.portfolio.travelAgency.service.dto.RoomDTO;
import com.portfolio.travelAgency.service.interfaces.BookingService;
import com.portfolio.travelAgency.service.interfaces.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class HotelController {

    private final RoomService roomService;
    private final BookingService bookingService;

    @GetMapping("/management/hotels/rooms")
    public String getRooms (@RequestParam("hotelId") Long hotelId,
                            Model model){

        List<RoomDTO> roomDTOS = roomService.findByHotelID(hotelId);
        model.addAttribute("rooms", roomDTOS);
        return "managerPages/rooms";
    }


    @GetMapping("/management/hotels/rooms/roomBookings")
    public String getBookings (@RequestParam("roomId") Long roomId,
                               Model model){

        List<BookingDTO> bookingDTOS = bookingService.findAllByRoom(roomId);
        model.addAttribute("bookings", bookingDTOS);
        return "managerPages/bookingsByRoom";
    }
}
