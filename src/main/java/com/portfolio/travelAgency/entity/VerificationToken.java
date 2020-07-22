package com.portfolio.travelAgency.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@Entity
public class VerificationToken {

    public VerificationToken() {
    }

    public VerificationToken(User user, int tokenTimeToLiveInHours) {
        this.user = user;
        this.token = UUID.randomUUID().toString();
        this.expiredDate = Instant.now().plus(tokenTimeToLiveInHours, ChronoUnit.HOURS);
    }

    @Id
    @GeneratedValue
    private Long id;

    private String token;

    private Instant expiredDate;

    @OneToOne
    private User user;

}
