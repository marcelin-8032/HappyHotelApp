package com.happyhotel.booking.service;

import com.happyhotel.booking.model.BookingRequest;

public interface IPaymentService {

    String pay(BookingRequest bookingRequest, double price);
}
