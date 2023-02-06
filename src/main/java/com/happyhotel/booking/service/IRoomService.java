package com.happyhotel.booking.service;

import com.happyhotel.booking.model.BookingRequest;
import com.happyhotel.booking.model.Room;

import java.util.List;

public interface IRoomService {

    String findAvailableRoomId(BookingRequest bookingRequest);

    List<Room> getAvailableRooms();

    int getRoomCount();

    void bookRoom(String roomId);

    void unbookRoom(String roomId);


}
