package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.RoomService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Override
    public List<Room> getAllRooms() {
        return List.of();
    }

    @Override
    public Room getRoomById(Long id) {
        return null;
    }

    @Override
    public Room createRoom(Long id) {
        return null;
    }

    @Override
    public Room deleteRoom(Long id) {
        return null;
    }

    @Override
    public Room updateRoom(Long id) {
        return null;
    }

    @Override
    public long getRoomCount() {
        return 0;
    }

    @Override
    public BigDecimal getTotalPrice() {
        return null;
    }
}
