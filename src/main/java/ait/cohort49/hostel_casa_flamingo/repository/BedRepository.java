package ait.cohort49.hostel_casa_flamingo.repository;

import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BedRepository extends JpaRepository<Bed, Long> {
    List<Bed> findByRoomId(Long roomId);
    boolean existsByRoomIdAndNumber(Long roomId, String number);
}
