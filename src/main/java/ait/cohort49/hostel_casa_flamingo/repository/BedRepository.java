package ait.cohort49.hostel_casa_flamingo.repository;

import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BedRepository extends JpaRepository<Bed, Long> {

    @Query("SELECT b FROM Bed b WHERE b.id NOT IN " +
            "(SELECT booking.bed.id FROM Booking booking " +
            "WHERE booking.entryDate <= :departureDate AND booking.departureDate >= :entryDate)")
    List<Bed> findAvailableBeds(@Param("entryDate") LocalDate entryDate,
                                @Param("departureDate") LocalDate departureDate);

    boolean existsByRoomIdAndNumber(Long roomId, String number);
}
