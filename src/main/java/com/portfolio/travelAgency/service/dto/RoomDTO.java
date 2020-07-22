package com.portfolio.travelAgency.service.dto;

import com.portfolio.travelAgency.entity.Booking;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.PrimaryKeyJoinColumn;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RoomDTO {

    private String number;

    private Integer price;

    private String type;

    private String hotel;

    private String city;

    private Set<Booking> bookings = new HashSet<>();

}
