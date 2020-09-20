package com.portfolio.travelAgency.controller.validator;

import com.portfolio.travelAgency.repository.HotelRepository;
import com.portfolio.travelAgency.service.dto.HotelDTO;
import com.portfolio.travelAgency.service.interfaces.CityService;
import com.portfolio.travelAgency.service.interfaces.HotelService;
import com.portfolio.travelAgency.service.mapper.HotelMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class AddHotelValidator implements Validator {

    private final HotelRepository hotelRepository;
    private final CityService cityService;

    @Override
    public boolean supports(Class<?> aClass) {
        return HotelDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        HotelDTO hotelDTO = (HotelDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "not.empty","Not empty space");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "not.empty","Not empty space");


        if (hotelRepository.findByNameAndCity(hotelDTO.getName(), cityService.findByName(hotelDTO.getCity())).isPresent())
            errors.rejectValue("name", "hotel.exist");



    }
}
