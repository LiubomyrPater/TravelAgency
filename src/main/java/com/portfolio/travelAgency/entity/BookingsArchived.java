package com.portfolio.travelAgency.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class BookingsArchived {

    @Id
    @GeneratedValue
    private int id;

    private Long oldId;

    private String user;

    private long room;

    private Integer price;

    private LocalDate arrival;

    private LocalDate departure;

    private LocalDateTime createMoment;

    private boolean lateDeparture;

    private boolean earlyArrival;

    private boolean paid;

    private LocalDateTime deleteMoment;
}
