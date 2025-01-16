package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.dto.BedDto;
import ait.cohort49.hostel_casa_flamingo.service.BedGetDto;

import java.util.List;

public interface BedService {

    BedDto saveBed(BedGetDto bedGetDto);

    BedDto getBedById(Long id);

    List<BedDto> getAllBeds();

    void deleteBedById(Long id);
}
