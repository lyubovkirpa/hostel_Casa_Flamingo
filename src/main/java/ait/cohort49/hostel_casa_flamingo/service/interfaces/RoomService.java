package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.entity.Room;

import java.math.BigDecimal;
import java.util.List;

public interface RoomService {

        List<Room> getAllRooms();
    Room getRoomById(Long id);
    Room findAllRooms(Long id);
    Room findRoomById (Long id);
    Room createRoom(Long id);
    Room deleteRoom(Long id);
    Room updateRoom(Long id);

        long getRoomCount();

        BigDecimal getTotalPrice();
}
