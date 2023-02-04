package com.happyhotel.booking;

import com.happyhotel.booking.dao.BookingDAO;
import com.happyhotel.booking.model.BookingRequest;
import com.happyhotel.booking.service.BookingService;
import com.happyhotel.booking.service.PaymentService;
import com.happyhotel.booking.service.RoomService;
import com.happyhotel.booking.tools.MailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class Test01FirstMocks {


    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;


    @BeforeEach
    void setup() {
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);


        this.bookingService = new BookingService(paymentServiceMock,
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
