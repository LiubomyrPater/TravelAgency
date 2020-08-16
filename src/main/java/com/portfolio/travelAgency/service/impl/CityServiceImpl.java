package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.entity.City;
import com.portfolio.travelAgency.repository.CityRepository;
import com.portfolio.travelAgency.service.interfaces.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public void save(City city) {
        cityRepository.save(city);
    }

    @Override
    public City findByName(String city) {
        return cityRepository.findByName(city)
                .orElseThrow(() -> new EntityNotFoundException("City with name " + city + " was not found"));
    }

    @Override
    public List<String> citiesName() {

        List<City> cities = cityRepository.findAll();
        List<String> cityName = new ArrayList<>();
        cities.forEach(x-> cityName.add(x.getName()));

        return cityName;
    }
}
