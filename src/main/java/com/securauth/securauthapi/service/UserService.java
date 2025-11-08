package com.securauth.securauthapi.service;

import com.securauth.securauthapi.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User createUser(User user);

    User findByUsername(String name);

    User updateUser(User user, String name);
}
