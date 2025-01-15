package ait.cohort49.hostel_casa_flamingo.controller;

import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.RoomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для обработки HTTP-запросов (CRUD-операций) с сущностью Room.
 */
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
    public List<Room> getAllRooms() {
        return roomService.findAllRooms();
    }

    /**
     * Найти комнату по ID (GET /rooms/{id}).
     */
    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Long id) {
        return roomService.findRoomById(id);
    }

    /**
     * Создать новую комнату (POST /rooms).
     */
    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        return roomService.createRoom();
    }

    /**
     * Обновить комнату (PUT /rooms/{id}).
     */
    @PutMapping("/{id}")
    public Room updateRoom(@PathVariable Long id, @RequestBody Room room) {
        return roomService.updateRoom(id);
    }

    /**
     * Удалить комнату (DELETE /rooms/{id}).
     */
    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
    }
}
