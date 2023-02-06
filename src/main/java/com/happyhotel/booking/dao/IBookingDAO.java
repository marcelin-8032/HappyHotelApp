package com.happyhotel.booking.dao;

import com.happyhotel.booking.model.BookingRequest;

public interface IBookingDAO {

    String save(BookingRequest bookingRequest);

    BookingRequest get(String id);

    void delete(String bookingId);
}
