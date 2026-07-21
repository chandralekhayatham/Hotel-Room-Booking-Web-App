package com.hotel.booking.utility;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BillCalculator {

    public static double calculateTotal(double pricePerNight,
                                        LocalDate checkIn,
                                        LocalDate checkOut) {

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);

        if (nights <= 0) {

            return 0;

        }

        return nights * pricePerNight;

    }

}