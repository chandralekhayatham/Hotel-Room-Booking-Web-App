package com.hotel.booking.service;

import com.hotel.booking.model.Room;
import com.hotel.booking.model.RoomType;
import com.hotel.booking.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    // ===============================
    // Add Room
    // ===============================

    public Room addRoom(Room room) {

        return roomRepository.save(room);

    }

    // ===============================
    // View All Rooms
    // ===============================

    public List<Room> getAllRooms() {

        return roomRepository.findAll();

    }

    // ===============================
    // Available Rooms
    // ===============================

    public List<Room> getAvailableRooms() {

        return roomRepository.findByAvailableTrue();

    }

    // ===============================
    // Search by Room Type
    // ===============================

    public List<Room> searchByRoomType(RoomType roomType) {

        return roomRepository.findByRoomTypeAndAvailableTrue(roomType);

    }

    // ===============================
    // Update Availability
    // ===============================

    public void updateAvailability(Long roomId,
                                   boolean available) {

        Room room =
                roomRepository.findById(roomId).orElse(null);

        if (room != null) {

            room.setAvailable(available);

            roomRepository.save(room);

        }

    }

    // ===============================
    // Delete Room
    // ===============================

    public void deleteRoom(Long roomId) {

        roomRepository.deleteById(roomId);

    }

}