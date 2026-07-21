package com.hotel.booking.controller;

import com.hotel.booking.model.*;
import com.hotel.booking.service.BookingService;
import com.hotel.booking.service.RoomService;
import com.hotel.booking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @GetMapping("/book")

    public String bookingPage() {

        return "booking";

    }

    @PostMapping("/book")

    public String createBooking(

            @RequestParam Long roomId,

            @RequestParam String email,

            @RequestParam String checkIn,

            @RequestParam String checkOut,

            @RequestParam int guests,

            Model model) {

        try {

            User user =
                    userService.getUserByEmail(email);

            Room room =
                    roomService.getAllRooms()

                            .stream()

                            .filter(r -> r.getId().equals(roomId))

                            .findFirst()

                            .orElse(null);

            Booking booking =
                    bookingService.createBooking(

                            user,

                            room,

                            LocalDate.parse(checkIn),

                            LocalDate.parse(checkOut),

                            guests

                    );

            model.addAttribute("booking", booking);

            return "booking-success";

        }

        catch (Exception e) {

            model.addAttribute("error",
                    e.getMessage());

            return "booking";

        }

    }

}