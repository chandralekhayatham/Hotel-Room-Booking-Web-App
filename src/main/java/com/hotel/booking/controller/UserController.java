package com.hotel.booking.controller;

import com.hotel.booking.model.User;
import com.hotel.booking.service.RoomService;
import com.hotel.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user,
                               Model model) {

        try {

            userService.register(user);

            model.addAttribute("success",
                    "Registration Successful.");

            return "login";

        } catch (Exception e) {

            model.addAttribute("error",
                    e.getMessage());

            return "register";
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            Model model) {

        User user = userService.login(email, password);

        if (user == null) {

            model.addAttribute("error",
                    "Invalid Email or Password");

            return "login";
        }

        if (user.getRole().name().equals("ADMIN")) {

            model.addAttribute("user", user);

            return "admin-dashboard";
        }

        model.addAttribute("user", user);

        model.addAttribute("rooms",
                roomService.getAvailableRooms());

        return "rooms";
    }
}