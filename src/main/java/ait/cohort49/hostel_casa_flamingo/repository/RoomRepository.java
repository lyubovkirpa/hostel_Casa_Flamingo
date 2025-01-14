package ait.cohort49.hostel_casa_flamingo.repository;

import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для сущности Room.
 * Наследуемся от JpaRepository<Room, Long>, чтобы иметь готовые
 * CRUD-методы: save, findById, findAll, deleteById и т.п.
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {


}
