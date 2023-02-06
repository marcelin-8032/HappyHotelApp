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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class Test01FirstMocks {

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
    void should_CalculateCorrectPrice_When_CorrectInput() {

        // given
        BookingRequest bookingRequest = new BookingRequest(
                "1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2,
                false);
        double expected = 4 * 2 * 50.0;
        //when
        double actual = bookingService.calculatePrice(bookingRequest);
        //then
        assertEquals(expected, actual);
    }


}
