package com.happyhotel.booking.service.impl;

import com.happyhotel.booking.dao.IBookingDAO;
import com.happyhotel.booking.service.IBookService;
import com.happyhotel.booking.service.IPaymentService;
import com.happyhotel.booking.service.IRoomService;
import com.happyhotel.booking.tools.CurrencyConverter;
import com.happyhotel.booking.tools.MailSender;
import com.happyhotel.booking.model.BookingRequest;

import java.time.temporal.ChronoUnit;

public class BookingServiceImpl implements IBookService {

    private final IPaymentService paymentService;
    private final IRoomService roomService;
    private final IBookingDAO bookingDAO;
    private final MailSender mailSender;

    private final static double BASE_PRICE_USD = 50.0;

    @Override
    public int getAvailablePlaceCount() {
        return roomService.getAvailableRooms()
                .stream()
                .map(room -> room.getCapacity())
                .reduce(0, Integer::sum);
    }

    @Override
    public double calculatePrice(BookingRequest bookingRequest) {
        long nights = ChronoUnit.DAYS.between(bookingRequest.getDateFrom(), bookingRequest.getDateTo());
        return BASE_PRICE_USD * bookingRequest.getGuestCount() * nights;
    }

    @Override
    public double calculatePriceEuro(BookingRequest bookingRequest) {
        long nights = ChronoUnit.DAYS.between(bookingRequest.getDateFrom(), bookingRequest.getDateTo());
        return CurrencyConverter.toEuro(BASE_PRICE_USD * bookingRequest.getGuestCount() * nights);
    }

    @Override
    public String makeBooking(BookingRequest bookingRequest) {
        String roomId = roomService.findAvailableRoomId(bookingRequest);
        double price = calculatePrice(bookingRequest);

        if (bookingRequest.isPrepaid()) {
            paymentService.pay(bookingRequest, price);
        }

        bookingRequest.setRoomId(roomId);
        String bookingId = bookingDAO.save(bookingRequest);
        roomService.bookRoom(roomId);
        mailSender.sendBookingConfirmation(bookingId);
        return bookingId;
    }

    @Override
    public void cancelBooking(String id) {
        BookingRequest request = bookingDAO.get(id);
        roomService.unbookRoom(request.getRoomId());
        bookingDAO.delete(id);
    }

    public BookingServiceImpl(IPaymentService paymentService, IRoomService roomService, IBookingDAO bookingDAO,
                              MailSender mailSender) {
        super();
        this.paymentService = paymentService;
        this.roomService = roomService;
        this.bookingDAO = bookingDAO;
        this.mailSender = mailSender;
    }
}
