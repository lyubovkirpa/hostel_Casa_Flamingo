package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.repository.BedRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BedService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BedServiceImpl implements BedService {

    private final BedRepository bedRepository;

    public BedServiceImpl(BedRepository bedRepository) {
        this.bedRepository = bedRepository;
    }

    @Override
    public Bed saveBed(Bed bed) {
        return bedRepository.save(bed);
    }

    @Override
    public Bed getBedById(Long id) {
        return bedRepository.findById(id).orElse(null);
    }

    @Override
    public List<Bed> getAllActiveBeds() {
        return bedRepository.findAll();
    }


    @Override
    public Bed updateBed(Long id, Bed bed) {
        return bedRepository.findById(id)
                .map(existingBed -> {
                    existingBed.setNumber(bed.getNumber());
                    existingBed.setType(bed.getType());
                    existingBed.setPrice(bed.getPrice());
                    return bedRepository.save(existingBed);
                })
                .orElse(null);
    }

    @Override
    public void deleteBedById(Long id) {
        bedRepository.deleteById(id);
    }

}
