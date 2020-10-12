package com.portfolio.travelAgency.controller.validator;

import com.portfolio.travelAgency.config.ApplicationProperties;
import com.portfolio.travelAgency.repository.UserRepository;
import com.portfolio.travelAgency.service.dto.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.validation.constraints.Pattern;

@Component
public class UserValidator implements Validator {

    private final UserRepository userRepository;
    private final ApplicationProperties applicationProperties;

    public UserValidator(UserRepository userRepository, ApplicationProperties applicationProperties) {
        this.userRepository = userRepository;
        this.applicationProperties = applicationProperties;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return UserDTO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        UserDTO userDTO = (UserDTO) o;


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "not.empty","Not empty space");


        if (userDTO.getName().trim().length() == 0)
            errors.rejectValue("name", "not.empty", "Not empty");

        if (userDTO.getSurname().trim().length() == 0)
            errors.rejectValue("surname", "not.empty", "Not empty");





        if (userDTO.getPassword().length() >= applicationProperties.getPasswordLength()){
            if (!userDTO.getPassword().equals(userDTO.getPasswordConfirm()))
                errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm", "Different password confirm");
            else {
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "not.space", "Not empty space");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "not.space", "Not empty space");
            }
        }else
            errors.rejectValue("password", "Size.userForm.password", "Size password 8");



        if (userDTO.getEmail().trim().length() == 0)
            errors.rejectValue("email","not.empty","Not empty space");
        else if(!userDTO.getEmail().trim().matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"))
            errors.rejectValue("email", "pattern.email", "Are you kidding?");
        else if (userRepository.existsByEmail(userDTO.getEmail()))
            errors.rejectValue("email", "email.exist", "User with these email already exist!");



        if (!userDTO.getPhoneNumber().matches("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$"))
            errors.rejectValue("phoneNumber","pattern.phoneNumber", "Phone number doesn't matches \"+001234567890\"");
        else if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber()))
            errors.rejectValue("phoneNumber", "phoneNumber.exist", "User with these phone number already exist!");


    }
}
