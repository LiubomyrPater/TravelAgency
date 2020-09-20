package com.portfolio.travelAgency.entity;

import lombok.Getter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;

@Getter
@Entity
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @UniqueElements
    private String name;

    @ManyToOne
    private Hotel hotel;

    private Integer price;

    @Override
    public String toString() {
        return name;
    }
}
