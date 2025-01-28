package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.dto.CreateOrUpdateRoomDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.RoomDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Room;

import java.math.BigDecimal;
import java.util.List;

public interface RoomService {

    List<RoomDto> getAllRooms();

    RoomDto getRoomById(Long id);

    RoomDto createRoom(CreateOrUpdateRoomDto roomDto);

    void deleteRoom(Long id);

    Room findByIdOrThrow(Long id);

    BigDecimal getTotalBedPriceForRoom(Long roomId);

    BigDecimal getTotalBedPriceForRoom(Room room);
}
