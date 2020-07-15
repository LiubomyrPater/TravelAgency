package com.portfolio.travelAgency.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;


@Getter
@Setter
@EqualsAndHashCode

@Entity
public class PersistentLogins {


    @Id
    private String series;

    private String username;

    private String token;

    private Timestamp last_used;
}
