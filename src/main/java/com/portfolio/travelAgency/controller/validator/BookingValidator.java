package com.portfolio.travelAgency.controller.validator;

import com.portfolio.travelAgency.service.dto.BookingDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class BookingValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return BookingDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        BookingDTO bookingDTO = (BookingDTO) o;


        if (!LocalDate.parse(bookingDTO.getDeparture()).isAfter(LocalDate.parse(bookingDTO.getArrival())))
            errors.rejectValue("departure", "not after arrival", "not after arrival");
    }
}
