package com.portfolio.travelAgency.service.interfaces;

import com.portfolio.travelAgency.entity.City;

import java.util.List;

public interface CityService {

    List<String> citiesName();

    City findByName(String city);

    void save(City city);
}
