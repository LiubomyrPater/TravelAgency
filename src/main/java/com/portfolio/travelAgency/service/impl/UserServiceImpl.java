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

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;


    @Override
    public void changeUserStatus(String user) {
        User persistedUser =userRepository.findByEmail(user).get();
        persistedUser.setEnabled(userRepository.findByEmail(user).get().isEnabled() ? false : true);
        userRepository.save(persistedUser);
    }

    @Override
    public List<UserDTO> findAllDTO() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + email + " was not found"));


    }

    @Override
    public List<String> usersName() {
        List<String> users = new ArrayList<>();
        userRepository.findAll().forEach(x -> users.add(x.getEmail()));
        return users;
    }




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
