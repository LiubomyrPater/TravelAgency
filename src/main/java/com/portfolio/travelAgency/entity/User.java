package com.portfolio.travelAgency.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
//@EqualsAndHashCode

@Entity
public class User implements UserDetails {


    @Id
    @GeneratedValue
    private Long id;


    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;


    @Column(nullable = false)
    private String password;


    @Transient
    private String passwordConfirm;


    @Email
    @Column(unique = true, nullable = false)
    private String email;


    @Column (unique = true, nullable = false)
    private String phoneNumber;


    private boolean enabled;


    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<Booking> bookings = new HashSet<>();



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn
    private Set<Role> role = new HashSet<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
