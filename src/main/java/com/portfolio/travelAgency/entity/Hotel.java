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
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private City city;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id")
    private Set<Room> rooms = new HashSet<>();

    @Override
    public String toString() {
        return name;
    }
}
