package com.hotel.booking.controller;

import com.hotel.booking.service.BookingService;
import com.hotel.booking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/admin")

    public String dashboard(Model model) {

        model.addAttribute("rooms",
                roomService.getAllRooms());

        model.addAttribute("bookings",
                bookingService.getAllBookings());

        model.addAttribute("totalBookings",
                bookingService.getTotalBookings());

        model.addAttribute("activeBookings",
                bookingService.getActiveBookings());

        model.addAttribute("checkedIn",
                bookingService.getCheckedInBookings());

        model.addAttribute("checkedOut",
                bookingService.getCheckedOutBookings());

        model.addAttribute("cancelled",
                bookingService.getCancelledBookings());

        return "admin-dashboard";

    }

}