package com.portfolio.travelAgency.controller.validator;

import com.portfolio.travelAgency.service.dto.BookingDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookingValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return BookingDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        BookingDTO bookingDTO = (BookingDTO) o;
    }
}
