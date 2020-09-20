package com.portfolio.travelAgency.controller.validator;

import com.portfolio.travelAgency.repository.RoomRepository;
import com.portfolio.travelAgency.service.dto.RoomDTO;
import com.portfolio.travelAgency.service.interfaces.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class AddRoomValidator implements Validator {

    private final RoomRepository roomRepository;
    private final HotelService hotelService;

    @Override
    public boolean supports(Class<?> aClass) {
        return RoomDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        RoomDTO roomDTO = (RoomDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "number", "not.empty","Not empty space");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "not.empty","Not empty space");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hotel", "not.empty","Not empty space");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "not.empty","Not empty space");

        if (roomRepository.findByNumberAndHotel(
                roomDTO.getNumber(), hotelService.findByNameAndCity(roomDTO.getHotel(),roomDTO.getCity()))
                .isPresent())
            errors.rejectValue("number", "room.exist");
    }
}
