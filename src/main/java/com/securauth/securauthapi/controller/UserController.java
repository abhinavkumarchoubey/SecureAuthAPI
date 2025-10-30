package com.securauth.securauthapi.controller;

import com.securauth.securauthapi.entity.User;
import com.securauth.securauthapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        try {
            return userService.getAllUsers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
