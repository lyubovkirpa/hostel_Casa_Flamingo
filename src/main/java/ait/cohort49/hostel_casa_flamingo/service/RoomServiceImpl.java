package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.dto.CreateOrUpdateRoomDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.RoomDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import ait.cohort49.hostel_casa_flamingo.repository.RoomRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.RoomService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.RoomMappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMappingService roomMappingService;

    public RoomServiceImpl(RoomRepository roomRepository, RoomMappingService roomMappingService) {
        this.roomRepository = roomRepository;
        this.roomMappingService = roomMappingService;
    }

    @Override
    public RoomDto getRoomById(Long id) {
        Room room = findByIdOrThrow(id);
        return createDtoWithTotalPrice(room);
    }

    private RoomDto createDtoWithTotalPrice(Room room) {
        BigDecimal priceForRoom = getTotalBedPriceForRoom(room);
        RoomDto roomDto = roomMappingService.mapEntityToDto(room);
        roomDto.setPrice(priceForRoom);
        return roomDto;
    }

    @Override
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::createDtoWithTotalPrice)
                .toList();
    }

    @Override
    public RoomDto createRoom(CreateOrUpdateRoomDto createRoomDto) {
        Room room = roomMappingService.mapDtoToEntity(createRoomDto);
        return roomMappingService.mapEntityToDto(roomRepository.save(room));
    }


    @Override
    public void deleteRoom(Long id) {
        findByIdOrThrow(id);
        roomRepository.deleteById(id);
    }

    @Override
    public Room findByIdOrThrow(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room by id: " + id + " not found"));
    }

    @Override
    public BigDecimal getTotalBedPriceForRoom(Long roomId) {
        Room selectedRoom = findByIdOrThrow(roomId);
        return getTotalBedPriceForRoom(selectedRoom);
    }

    @Override
    public BigDecimal getTotalBedPriceForRoom(Room room) {
        return room.getBeds()
                .stream()
                .map(Bed::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}