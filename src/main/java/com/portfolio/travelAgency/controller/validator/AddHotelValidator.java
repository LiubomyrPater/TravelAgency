package com.portfolio.travelAgency.controller.validator;

import com.portfolio.travelAgency.service.dto.HotelDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddHotelValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return HotelDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        HotelDTO hotelDTO = (HotelDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "not.empty","Not empty space");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "not.empty","Not empty space");





    }
}
