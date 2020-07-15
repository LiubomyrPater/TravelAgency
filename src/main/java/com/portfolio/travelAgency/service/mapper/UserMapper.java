package com.portfolio.travelAgency.service.mapper;

import com.portfolio.travelAgency.entity.User;
import com.portfolio.travelAgency.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public User toEntity(UserDTO userDTO) {

        User result = new User();

        result.setName(userDTO.getName());
        result.setSurname(userDTO.getSurname());
        result.setPassword(userDTO.getPassword());
        result.setEmail(userDTO.getEmail());
        result.setPhoneNumber(userDTO.getPhoneNumber());
        result.setRole(userDTO.getRole());

        return result;
    }


    public UserDTO toDTO(User user) {

        UserDTO result = new UserDTO();

        result.setId(user.getId());
        result.setName(user.getName());
        result.setSurname(user.getSurname());
        result.setEmail(user.getEmail());
        result.setPhoneNumber(user.getPhoneNumber());
        result.setRole(user.getRole());
        result.setEnabled(user.isEnabled());

        return result;
    }
}
