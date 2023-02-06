package com.happyhotel.booking.service.impl;

import com.happyhotel.booking.model.BookingRequest;
import com.happyhotel.booking.service.IPaymentService;

import java.util.*;

public class PaymentServiceImpl implements IPaymentService {

    private final Map<String, Double> payments = new HashMap<>();

    @Override
    public String pay(BookingRequest bookingRequest, double price) {
        if (price > 200.0 && bookingRequest.getGuestCount() < 3) {
            throw new UnsupportedOperationException("Only small payments are supported.");
        }
        String id = UUID.randomUUID().toString();
        payments.put(id, price);
        return id;
    }

}
