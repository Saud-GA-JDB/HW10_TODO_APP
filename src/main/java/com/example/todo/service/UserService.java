package com.example.todo.service;


import com.example.todo.exception.InformationExistException;
import com.example.todo.model.User;

import com.example.todo.model.request.LoginRequest;
import com.example.todo.model.request.response.LoginResponse;
import com.example.todo.repository.UserRepository;

import com.example.todo.security.JWTUtils;
import com.example.todo.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private MyUserDetails myUserDetails;


    @Autowired
    public UserService(
            UserRepository userRepository,
            @Lazy PasswordEncoder passwordEncoder,
            JWTUtils jwtUtils,
            @Lazy AuthenticationManager authenticationManager,
            @Lazy MyUserDetails myUserDetails
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.myUserDetails = myUserDetails;
    }

    public User createUser(User userObject) {
        System.out.println("Service Calling createUser");

        if (!userRepository.existsByEmailAddress(userObject.getEmailAddress())) {
            return userRepository.save(userObject);
        } else {
            throw new InformationExistException("User with email address" + " already exists.");
        }
    }

    public User findUserByEmailAddress(String email) {
        return userRepository.findUserByEmailAddress(email);
    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            myUserDetails = (MyUserDetails) authentication.getPrincipal();
            final String JWT = jwtUtils.generateJwtToken(myUserDetails);
            return ResponseEntity.ok(new LoginResponse(JWT));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new LoginResponse("Error: User name or email are incorrect!"));
        }
    }
}

