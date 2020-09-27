package com.portfolio.travelAgency.service.dto;

import com.portfolio.travelAgency.entity.Booking;
import com.portfolio.travelAgency.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

public class UserDTO {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String passwordConfirm;

    private String phoneNumber;

    private boolean enabled;

   // private Set<Booking> bookings = new HashSet<>();

    private Set<Role> role = new HashSet<>();

    @Override
    public String toString() {
        return "UserDTO{" +
                "email='" + email + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
