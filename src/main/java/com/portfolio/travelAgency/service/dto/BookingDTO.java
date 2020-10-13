package com.portfolio.travelAgency.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingDTO {

    private Long id;

    private String user;

    private String city;

    private String hotel;

    private String typeRoom;

    private String room;

    private Integer price;

    private String arrival;

    private String departure;

    private LocalDateTime createMoment;

    private boolean lateDeparture;

    private boolean earlyArrival;

    private boolean paid;
}
