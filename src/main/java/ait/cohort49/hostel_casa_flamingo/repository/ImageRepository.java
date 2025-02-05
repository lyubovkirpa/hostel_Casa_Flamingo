package ait.cohort49.hostel_casa_flamingo.repository;

import ait.cohort49.hostel_casa_flamingo.model.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByBedId(Long bedId);
    List<Image> findByRoomId(Long roomId);
}
