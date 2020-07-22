package com.portfolio.travelAgency.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Booking {


    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Room room;

    private Integer price;

    private LocalDate arrival;

    private LocalDate departure;

    private LocalDateTime createMoment;

    private boolean lateDeparture;

    private boolean earlyArrival;

    //private boolean paid;

}
