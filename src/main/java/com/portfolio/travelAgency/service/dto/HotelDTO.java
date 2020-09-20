package com.portfolio.travelAgency.service.dto;

import com.portfolio.travelAgency.entity.Room;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class HotelDTO {

    private Long id;

    private String name;

    private String city;

    //private Set<Room> rooms = new HashSet<>();
}
