package com.example.todo.service;


import com.example.todo.exception.InformationExistException;
import com.example.todo.model.User;

import com.example.todo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;


    @Autowired
    public UserService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
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

}

