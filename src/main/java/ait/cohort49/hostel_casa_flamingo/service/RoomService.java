package ait.cohort49.hostel_casa_flamingo.service;


import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import ait.cohort49.hostel_casa_flamingo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис-слой для работы с сущностью Room.
 * Здесь размещается бизнес-логика (проверки, дополнительные расчёты и т.д.).
 */
@Service
public class RoomService {

    private final RoomRepository roomRepository;

    /**
     * Внедрение RoomRepository через конструктор.
     */
    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * Получить все комнаты.
     */
    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    /**
     * Найти комнату по ID. Если не найдена — выбрасываем исключение.
     */
    public Room findRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Комната не найдена по id: " + id));
    }

    /**
     * Создать (сохранить) новую комнату.
     */
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    /**
     * Обновить существующую комнату по ID.
     */
    public Room updateRoom(Long id, Room updatedRoom) {
        Room existingRoom = findRoomById(id);
        // Обновляем нужные поля
        existingRoom.setNumber(updatedRoom.getNumber());
        existingRoom.setType(updatedRoom.getType());
        existingRoom.setBeds(updatedRoom.getBeds());
        // Сохраняем
        return roomRepository.save(existingRoom);
    }

    /**
     * Удалить комнату по ID.
     */
    public void deleteRoom(Long id) {
        Room existingRoom = findRoomById(id);
        roomRepository.delete(existingRoom);
    }
}
