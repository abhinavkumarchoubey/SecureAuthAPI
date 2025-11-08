package com.securauth.securauthapi.controller;

import com.securauth.securauthapi.entity.User;
import com.securauth.securauthapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){
        try {
            List<User> allUsers = userService.getAllUsers();
            return ResponseEntity.ok(allUsers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/me")
    @PreAuthorize("#username == authentication.name or hasRole('ADMIN')")
    public ResponseEntity<User> findUserByUsername(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String name=authentication.getName();
        return ResponseEntity.ok(userService.findByUsername(name));
    }

    @PostMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String name=authentication.getName();
        return ResponseEntity.ok(userService.updateUser(user, name));
    }

}
