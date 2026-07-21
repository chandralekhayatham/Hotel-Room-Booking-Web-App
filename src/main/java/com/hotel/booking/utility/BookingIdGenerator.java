package com.hotel.booking.utility;

import java.util.UUID;

public class BookingIdGenerator {

    public static String generateBookingId() {

        return "BK-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();

    }

}