package com.hotel.booking.utility;

import java.time.LocalDate;

public class DateValidator {

    public static boolean isValidBookingDate(LocalDate checkIn,
                                             LocalDate checkOut) {

        if (checkIn == null || checkOut == null) {

            return false;

        }

        if (checkIn.isBefore(LocalDate.now())) {

            return false;

        }

        return checkOut.isAfter(checkIn);

    }

}