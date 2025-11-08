package com.securauth.securauthapi.service.impl;

import com.securauth.securauthapi.entity.User;
import com.securauth.securauthapi.repository.UserRepository;
import com.securauth.securauthapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(User.UserRole.ROLE_USER);
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public User updateUser(User user, String name) {
        User userAfterUpdate = userRepository.findByUsername(name);
        userAfterUpdate.setName(user.getName());
        userAfterUpdate.setUsername(user.getUsername());
        userAfterUpdate.setEmail(user.getEmail());
        userAfterUpdate.setPhone(user.getPhone());
        userAfterUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userAfterUpdate.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(userAfterUpdate);
    }
}
