package com.happyhotel.booking.service;

import com.happyhotel.booking.model.BookingRequest;

public interface IBookService {

    int getAvailablePlaceCount();

    double calculatePrice(BookingRequest bookingRequest);

    double calculatePriceEuro(BookingRequest bookingRequest);

    String makeBooking(BookingRequest bookingRequest);

    void cancelBooking(String id);
}
