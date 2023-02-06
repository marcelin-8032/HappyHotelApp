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
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class Test10ArgumentCaptors {

    private IBookService bookingService;
    private IPaymentService paymentServiceMock;
    private IRoomService roomServiceMock;
    private IBookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    private ArgumentCaptor<Double> doubleCaptor;

    @BeforeEach
    void setup() {
        this.paymentServiceMock = mock(IPaymentService.class);
        this.roomServiceMock = mock(IRoomService.class);
        this.bookingDAOMock = mock(IBookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingServiceImpl(paymentServiceMock,
                roomServiceMock, bookingDAOMock, mailSenderMock);

        this.doubleCaptor = ArgumentCaptor.forClass(Double.class);
    }
    @Test
    void should_PayCorrectPrice_When_InputOk() {
        // given
        BookingRequest bookingRequest = new BookingRequest(
                "1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2,
                true);
        //when
        bookingService.makeBooking(bookingRequest);

        //then
        verify(paymentServiceMock, times(1))
                .pay(eq(bookingRequest), doubleCaptor.capture());

        double capturedArgument = doubleCaptor.getValue();

        assertEquals(400.0, capturedArgument);
    }


    @Test
    void should_PayCorrectPrice_When_MutlipleCalls() {
        // given
        BookingRequest bookingRequest = new BookingRequest(
                "1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2,
                true);

        BookingRequest bookingRequest2 = new BookingRequest(
                "1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 02), 2,
                true);

        List<Double> expectedValues = Arrays.asList(400.0, 100.0);

        //when
        bookingService.makeBooking(bookingRequest);
        bookingService.makeBooking(bookingRequest2);

        //then
        verify(paymentServiceMock, times(2))
                .pay(any(), doubleCaptor.capture());

        List<Double> capturedArguments = doubleCaptor.getAllValues();

        assertEquals(expectedValues, capturedArguments);


    }
}
