package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.dto.BedDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.service.BedCreateDto;

import java.util.List;

public interface BedService {

    BedDto saveBed(BedCreateDto bedCreateDto);

    Bed getBedOrThrow(long id);

    BedDto getBedById(Long id);

    List<BedDto> getAllBeds();

    void deleteBedById(Long id);
}
