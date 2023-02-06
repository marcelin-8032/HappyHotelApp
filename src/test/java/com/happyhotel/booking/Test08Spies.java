package com.happyhotel.booking;

import com.happyhotel.booking.dao.IBookingDAO;
import com.happyhotel.booking.model.BookingRequest;
import com.happyhotel.booking.service.IBookService;
import com.happyhotel.booking.service.IPaymentService;
import com.happyhotel.booking.service.IRoomService;
import com.happyhotel.booking.service.impl.BookingServiceImpl;
import com.happyhotel.booking.tools.MailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

class Test08Spies {

    private IBookService bookingService;
    private IPaymentService paymentServiceMock;
    private IRoomService roomServiceMock;
    private IBookingDAO bookingDAOMock;
    private MailSender mailSenderMock;


    @BeforeEach
    void setup() {
        this.paymentServiceMock = mock(IPaymentService.class);
        this.roomServiceMock = mock(IRoomService.class);
        this.bookingDAOMock = mock(IBookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingServiceImpl(paymentServiceMock,
                roomServiceMock, bookingDAOMock, mailSenderMock);

    }

    @Test
    void should_MakeBooking_When_InputOK() {
        // given
        BookingRequest bookingRequest = new BookingRequest(
                "1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2,
                true);
        //when
        String bookingId = bookingService.makeBooking(bookingRequest);

        //then
        verify(bookingDAOMock).save(bookingRequest);
        System.out.println("bookingId=" + bookingId);

    }
    @Test
    void should_CancelBooking_When_InputOK() {
        // given
        BookingRequest bookingRequest = new BookingRequest(
                "1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2,
                true);
        bookingRequest.setRoomId("1.3");
        String bookingId = "1";

        doReturn(bookingRequest).when(bookingDAOMock).get(bookingId);

        //when
        bookingService.cancelBooking(bookingId);

        //then


    }

}
