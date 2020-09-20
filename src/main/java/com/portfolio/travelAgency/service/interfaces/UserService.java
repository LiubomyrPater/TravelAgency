package com.portfolio.travelAgency.service.interfaces;

import com.portfolio.travelAgency.entity.User;
import com.portfolio.travelAgency.service.dto.UserDTO;

import java.util.List;

public interface UserService {

    void confirmRegistration(String token);

    User registerNewUser(UserDTO userDTO);

    List<String> usersName();

    User findByEmail(String email);

    List<UserDTO> findAllDTO();
}
