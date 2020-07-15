package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.entity.User;
import com.portfolio.travelAgency.entity.VerificationToken;
import com.portfolio.travelAgency.exception.UserAlreadyExistException;
import com.portfolio.travelAgency.repository.RoleRepository;
import com.portfolio.travelAgency.repository.UserRepository;
import com.portfolio.travelAgency.repository.VerificationTokenRepository;
import com.portfolio.travelAgency.service.dto.UserDTO;
import com.portfolio.travelAgency.service.interfaces.UserService;
import com.portfolio.travelAgency.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User registerNewUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        if (userRepository.existsByEmail(user.getEmail())
                || userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new UserAlreadyExistException();
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.getRole().add(roleRepository.findByName("ROLE_USER"));

        userRepository.save(user);
        return user;
    }

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    @Override
    public void confirmRegistration(String token) {
        VerificationToken tokenPersisted = verificationTokenRepository.findByToken(token);
        if (tokenPersisted != null && tokenIsValid(tokenPersisted)) {
            User user = tokenPersisted.getUser();
            user.setEnabled(true);
            userRepository.save(user);
        }
    }

    private boolean tokenIsValid(VerificationToken token) {
        return Duration.between(token.getExpiredDate(), Instant.now()).isNegative();
    }
}
