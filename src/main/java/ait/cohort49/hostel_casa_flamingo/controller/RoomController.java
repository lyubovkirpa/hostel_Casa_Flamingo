package ait.cohort49.hostel_casa_flamingo.controller;

import ait.cohort49.hostel_casa_flamingo.model.dto.RoomDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    /**
     * Получить список всех комнат (GET /rooms).
     */
    @GetMapping
    public List<RoomDto> getAllRooms() {
        return roomService.getAllRooms();
    }

    /**
     * Найти комнату по ID (GET /rooms/{id}).
     */
    @GetMapping("/{id}")
    public RoomDto getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    /**
     * Создать новую комнату (POST /rooms).
     */
    @PostMapping
    public RoomDto createRoom(@RequestBody RoomDto roomDto) {
        return roomService.createRoom(roomDto);
    }

//    /**
//     * Обновить комнату (PUT /rooms/{id}).
//     */
//    @PutMapping("/{id}")
//    public RoomDto updateRoom(@PathVariable Long id, @RequestBody RoomDto room) {
//        return roomService.updateRoom(id);
//    }

    /**
     * Удалить комнату (DELETE /rooms/{id}).
     */
    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }
}
