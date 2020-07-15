package com.portfolio.travelAgency.repository;

import com.portfolio.travelAgency.entity.City;
import com.portfolio.travelAgency.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    //List<City> findAllBy();


    Optional<City> findByName(String name);
}
