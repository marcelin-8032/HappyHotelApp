package com.happyhotel.booking;

import com.happyhotel.booking.dao.IBookingDAO;
import com.happyhotel.booking.exception.BusinessException;
import com.happyhotel.booking.model.BookingRequest;
import com.happyhotel.booking.service.IBookService;
import com.happyhotel.booking.service.IPaymentService;
import com.happyhotel.booking.service.IRoomService;
import com.happyhotel.booking.service.impl.BookingServiceImpl;
import com.happyhotel.booking.tools.MailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class Test09MockingVoidMethods {


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
    void should_ThrowException_When_MailNotReady() {

        // given
        BookingRequest bookingRequest = new BookingRequest(
                "1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2,
                false);

        doThrow(new BusinessException())
                .when(mailSenderMock)
                .sendBookingConfirmation(any());

        //when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);

        //then
        assertThrows(BusinessException.class, executable);

    }


    @Test
    void should_NotThrowException_When_MailNotReady() {

        // given
        BookingRequest bookingRequest = new BookingRequest(
                "1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2,
                false);

        doNothing()
                .when(mailSenderMock)
                .sendBookingConfirmation(any());

        //when
        //then
        //no exception thrown

    }

}
