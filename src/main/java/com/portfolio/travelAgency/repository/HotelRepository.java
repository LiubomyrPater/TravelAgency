package com.portfolio.travelAgency.repository;

import com.portfolio.travelAgency.entity.City;
import com.portfolio.travelAgency.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findByName(String name);

    Optional<Hotel> findByNameAndCity(String name, City city);

    List<Hotel> findByCity(City city);



}
