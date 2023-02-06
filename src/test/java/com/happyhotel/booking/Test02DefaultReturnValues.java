package com.happyhotel.booking;

import com.happyhotel.booking.dao.IBookingDAO;
import com.happyhotel.booking.service.IBookService;
import com.happyhotel.booking.service.IPaymentService;
import com.happyhotel.booking.service.IRoomService;
import com.happyhotel.booking.service.impl.BookingServiceImpl;
import com.happyhotel.booking.tools.MailSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class Test02DefaultReturnValues {

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

        System.out.println("List returned " + roomServiceMock.getAvailableRooms());
        System.out.println("Object returned " + roomServiceMock.findAvailableRoomId(null));
        System.out.println("Primitive returned " + roomServiceMock.getRoomCount());

    }


    @Test
    void should_CountAvailablePlaces() {
        // given
        int expected = 0;
        //when

        int actual = bookingService.getAvailablePlaceCount();

        //then
        assertEquals(expected, actual);

    }


}
