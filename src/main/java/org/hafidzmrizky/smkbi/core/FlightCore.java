package org.hafidzmrizky.smkbi.core;

import org.hafidzmrizky.smkbi.dao.BookingDao;
import org.hafidzmrizky.smkbi.model.Booking;

import java.util.Collection;

public class FlightCore {



    public static int totalTicketSales() {
        BookingDao bookingDao = new BookingDao();
        Collection<Booking> bookings = bookingDao.getAll();
        int total = 0;
        for (Booking booking : bookings) {
            total += booking.getPrice();
        }
        return total;
    }


    public void addFlight() {

    }

}
