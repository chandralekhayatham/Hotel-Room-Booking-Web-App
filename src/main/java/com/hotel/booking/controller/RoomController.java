package com.hotel.booking.controller;

import com.hotel.booking.model.Room;
import com.hotel.booking.model.RoomType;
import com.hotel.booking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/rooms")

    public String rooms(Model model) {

        model.addAttribute("rooms",
                roomService.getAvailableRooms());

        return "rooms";

    }

    @GetMapping("/rooms/type/{type}")

    public String searchRoom(

            @PathVariable RoomType type,

            Model model) {

        List<Room> rooms =
                roomService.searchByRoomType(type);

        model.addAttribute("rooms", rooms);

        return "rooms";

    }

}