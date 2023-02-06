package com.happyhotel.booking.dao;

import com.happyhotel.booking.model.BookingRequest;

import java.util.*;

public class BookingDAO implements IBookingDAO {

    private final Map<String, BookingRequest> bookings = new HashMap<>();

    @Override
    public String save(BookingRequest bookingRequest) {
        String id = UUID.randomUUID().toString();
        bookings.put(id, bookingRequest);
        return id;
    }

    @Override
    public BookingRequest get(String id) {
        return bookings.get(id);
    }

    @Override
    public void delete(String bookingId) {
        bookings.remove(bookingId);
    }

}
