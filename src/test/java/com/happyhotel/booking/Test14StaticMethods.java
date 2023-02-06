package com.happyhotel.booking;

import com.happyhotel.booking.dao.IBookingDAO;
import com.happyhotel.booking.model.BookingRequest;
import com.happyhotel.booking.service.IPaymentService;
import com.happyhotel.booking.service.IRoomService;
import com.happyhotel.booking.service.impl.BookingServiceImpl;
import com.happyhotel.booking.tools.CurrencyConverter;
import com.happyhotel.booking.tools.MailSender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class Test14StaticMethods {

    @InjectMocks
    private BookingServiceImpl bookingService;
    @Mock
    private IPaymentService paymentServiceMock;
    @Mock
    private IRoomService roomServiceMock;
    @Mock
    //@Spy
    private IBookingDAO bookingDAOMock;
    @Mock
    private MailSender mailSenderMock;
    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

    @Test
    void should_CalculateCorrectPrice() {
        try (MockedStatic<CurrencyConverter> mockedConverter =
                     mockStatic(CurrencyConverter.class)) {
            // given
            BookingRequest bookingRequest = new BookingRequest(
                    "1", LocalDate.of(2020, 01, 01),
                    LocalDate.of(2020, 01, 05), 2,
                    false);

            double expected = 400.0;
            mockedConverter.when(
                            () -> CurrencyConverter.toEuro(anyDouble()))
                    .thenReturn(400.0);

            //when
            double actual = bookingService.calculatePrice(bookingRequest);

            //then
            assertEquals(expected, actual);
        }


    }
}
