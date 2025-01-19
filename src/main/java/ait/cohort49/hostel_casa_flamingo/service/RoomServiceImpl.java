package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.dto.RoomDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import ait.cohort49.hostel_casa_flamingo.repository.RoomRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.RoomService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.RoomMappingService;
import org.springframework.stereotype.Service;

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
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room by id: " + id + " not found"));
        return roomMappingService.mapEntityToDto(room);
    }

    @Override
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(roomMappingService::mapEntityToDto)
                .toList();
    }

    @Override
    public RoomDto createRoom(RoomDto roomDto) {
        Room room = roomMappingService.mapDtoToEntity(roomDto);
        return roomMappingService.mapEntityToDto(roomRepository.save(room));
    }

    /**
     * public RoomDto updateRoom(Long id) {
     * return roomRepository.updateRoom(id);
     * }
     */

    @Override
    public void deleteRoom(Long id) {
        roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room by id: " + id + " not found"));
        roomRepository.deleteById(id);
    }

}