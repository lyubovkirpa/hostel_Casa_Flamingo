package ait.cohort49.hostel_casa_flamingo.repository;

import ait.cohort49.hostel_casa_flamingo.model.entity.Booking;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUser(User authUser);

    boolean existsByIdAndDepartureDateAfter(Long bedId, LocalDate date);

    List<Booking> findBedByIdAndDepartureDateBefore(Long bedId, LocalDate date);



}
