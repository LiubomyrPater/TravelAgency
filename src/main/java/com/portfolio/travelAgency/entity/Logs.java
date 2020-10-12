package com.portfolio.travelAgency.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;



@Entity
@Getter
@Setter
public class Logs {

    @Id
    private Long ID;

    private Long user_ID;

    private LocalDateTime dated;

    private String level;

    private String message;
}
