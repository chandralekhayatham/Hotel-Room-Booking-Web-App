package com.hotel.booking.repository;

import com.hotel.booking.model.Room;
import com.hotel.booking.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByAvailableTrue();

    List<Room> findByRoomType(RoomType roomType);

    List<Room> findByRoomTypeAndAvailableTrue(RoomType roomType);

}