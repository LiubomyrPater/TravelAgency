package com.portfolio.travelAgency.entity;

import lombok.Getter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @UniqueElements
    private String name;

    @Override
    public String toString() {
        return name;
    }
}
