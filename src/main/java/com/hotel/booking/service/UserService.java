package com.hotel.booking.service;

import com.hotel.booking.model.User;
import com.hotel.booking.model.UserRole;
import com.hotel.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ===============================
    // Register User
    // ===============================

    public User register(User user) {

        Optional<User> existingUser =
                userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {

            throw new RuntimeException("Email already exists.");

        }

        user.setRole(UserRole.CUSTOMER);

        return userRepository.save(user);

    }

    // ===============================
    // Login
    // ===============================

    public User login(String email,
                      String password) {

        Optional<User> user =
                userRepository.findByEmail(email);

        if (user.isPresent()
                &&
                user.get().getPassword().equals(password)) {

            return user.get();

        }

        return null;

    }

    // ===============================
    // Find User
    // ===============================

    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email).orElse(null);

    }

}