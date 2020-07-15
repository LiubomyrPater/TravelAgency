package com.portfolio.travelAgency.service.interfaces;

import com.portfolio.travelAgency.entity.User;
import com.portfolio.travelAgency.service.dto.UserDTO;

public interface UserService {

    void confirmRegistration(String token);

    User registerNewUser(UserDTO userDTO);
}
