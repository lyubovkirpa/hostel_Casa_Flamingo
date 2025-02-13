package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.dto.AvailableBedDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.BedDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.CreateBedDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;

import java.time.LocalDate;
import java.util.List;

public interface BedService {

    BedDto saveBed(CreateBedDto createBedDto);

    Bed getBedOrThrow(long id);

    BedDto getBedById(Long id);

    List<BedDto> getAllBeds();

    void deleteBedById(Long id);

    List<AvailableBedDto> getAvailableBeds(Long id, LocalDate entryDate, LocalDate departureDate);

    BedDto mapBedToDtoWithImages(Bed bed);
}
