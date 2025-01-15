package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;

import java.util.List;

public interface BedService {

    Bed saveBed(Bed bed);

    Bed getBedById(Long id);

    List<Bed> getAllBeds();

    void deleteBedById(Long id);


}
