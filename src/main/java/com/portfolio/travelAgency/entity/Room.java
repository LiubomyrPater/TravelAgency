package com.portfolio.travelAgency.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
//@EqualsAndHashCode

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Hotel hotel;

    private String number;

    private Integer price;

    @ManyToOne
    private RoomType type;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Set<Booking> bookings = new HashSet<>();

    @Override
    public String toString() {
        return number;
    }
}
