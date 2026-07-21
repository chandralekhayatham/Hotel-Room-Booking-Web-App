package com.hotel.booking.service;

import com.hotel.booking.model.*;
import com.hotel.booking.repository.BookingRepository;
import com.hotel.booking.repository.RoomRepository;
import com.hotel.booking.utility.BillCalculator;
import com.hotel.booking.utility.BookingIdGenerator;
import com.hotel.booking.utility.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    // =====================================
    // Create Booking
    // =====================================

    public Booking createBooking(User user,
                                 Room room,
                                 LocalDate checkIn,
                                 LocalDate checkOut,
                                 int guests) {

        if (!DateValidator.isValidBookingDate(checkIn, checkOut)) {

            throw new RuntimeException("Invalid Booking Dates.");

        }

        if (!room.isAvailable()) {

            throw new RuntimeException("Room Not Available.");

        }

        if (guests > room.getMaxGuests()) {

            throw new RuntimeException("Maximum Guest Limit Exceeded.");

        }

        double totalAmount =
                BillCalculator.calculateTotal(

                        room.getPricePerNight(),

                        checkIn,

                        checkOut

                );

        Booking booking = new Booking(

                BookingIdGenerator.generateBookingId(),

                user,

                room,

                checkIn,

                checkOut,

                guests,

                totalAmount,

                BookingStatus.BOOKED

        );

        room.setAvailable(false);

        roomRepository.save(room);

        return bookingRepository.save(booking);

    }

    // =====================================
    // View Booking by Booking ID
    // =====================================

    public Booking getBooking(String bookingId) {

        return bookingRepository.findByBookingId(bookingId)

                .orElse(null);

    }

    // =====================================
    // View User Bookings
    // =====================================

    public List<Booking> getUserBookings(User user) {

        return bookingRepository.findByUser(user);

    }
        // =====================================
    // Cancel Booking
    // =====================================

    public void cancelBooking(String bookingId) {

        Booking booking = getBooking(bookingId);

        if (booking == null) {

            throw new RuntimeException("Booking Not Found.");

        }

        booking.setStatus(BookingStatus.CANCELLED);

        Room room = booking.getRoom();

        room.setAvailable(true);

        roomRepository.save(room);

        bookingRepository.save(booking);

    }

    // =====================================
    // Check In
    // =====================================

    public void checkIn(String bookingId) {

        Booking booking = getBooking(bookingId);

        if (booking == null) {

            throw new RuntimeException("Booking Not Found.");

        }

        booking.setStatus(BookingStatus.CHECKED_IN);

        bookingRepository.save(booking);

    }

    // =====================================
    // Check Out
    // =====================================

    public void checkOut(String bookingId) {

        Booking booking = getBooking(bookingId);

        if (booking == null) {

            throw new RuntimeException("Booking Not Found.");

        }

        booking.setStatus(BookingStatus.CHECKED_OUT);

        Room room = booking.getRoom();

        room.setAvailable(true);

        roomRepository.save(room);

        bookingRepository.save(booking);

    }

    // =====================================
    // View All Bookings
    // =====================================

    public List<Booking> getAllBookings() {

        return bookingRepository.findAll();

    }

    // =====================================
    // Search Booking by Status
    // =====================================

    public List<Booking> getBookingsByStatus(BookingStatus status) {

        return bookingRepository.findByStatus(status);

    }

    // =====================================
    // Update Booking Status
    // =====================================

    public Booking updateBookingStatus(String bookingId,
                                       BookingStatus status) {

        Booking booking = getBooking(bookingId);

        if (booking == null) {

            throw new RuntimeException("Booking Not Found.");

        }

        booking.setStatus(status);

        return bookingRepository.save(booking);

    }
        // =====================================
    // Delete Booking
    // =====================================

    public void deleteBooking(String bookingId) {

        Booking booking = getBooking(bookingId);

        if (booking == null) {

            throw new RuntimeException("Booking Not Found.");

        }

        Room room = booking.getRoom();

        room.setAvailable(true);

        roomRepository.save(room);

        bookingRepository.delete(booking);

    }

    // =====================================
    // Total Bookings
    // =====================================

    public long getTotalBookings() {

        return bookingRepository.count();

    }

    // =====================================
    // Total Active Bookings
    // =====================================

    public long getActiveBookings() {

        return bookingRepository
                .findByStatus(BookingStatus.BOOKED)
                .size();

    }

    // =====================================
    // Total Checked In
    // =====================================

    public long getCheckedInBookings() {

        return bookingRepository
                .findByStatus(BookingStatus.CHECKED_IN)
                .size();

    }

    // =====================================
    // Total Cancelled
    // =====================================

    public long getCancelledBookings() {

        return bookingRepository
                .findByStatus(BookingStatus.CANCELLED)
                .size();

    }

    // =====================================
    // Total Checked Out
    // =====================================

    public long getCheckedOutBookings() {

        return bookingRepository
                .findByStatus(BookingStatus.CHECKED_OUT)
                .size();

    }

    // =====================================
    // Dashboard Statistics
    // =====================================

    public void printStatistics() {

        System.out.println();

        System.out.println("================================");

        System.out.println(" HOTEL BOOKING DASHBOARD ");

        System.out.println("================================");

        System.out.println("Total Bookings : "
                + getTotalBookings());

        System.out.println("Booked : "
                + getActiveBookings());

        System.out.println("Checked In : "
                + getCheckedInBookings());

        System.out.println("Checked Out : "
                + getCheckedOutBookings());

        System.out.println("Cancelled : "
                + getCancelledBookings());

        System.out.println("================================");

    }

}