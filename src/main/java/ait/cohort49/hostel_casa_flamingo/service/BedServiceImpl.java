package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.dto.BedDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.repository.BedRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BedService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.BedMappingService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BedServiceImpl implements BedService {

    private final BedRepository bedRepository;
    private final BedMappingService bedMappingService;

    public BedServiceImpl(BedRepository bedRepository, BedMappingService bedMappingService) {
        this.bedRepository = bedRepository;
        this.bedMappingService = bedMappingService;
    }

    @Override
    public BedDto saveBed(BedGetDto bedGetDto) {
        Bed bed = bedMappingService.mapDtoToEntity(bedGetDto);
        return bedMappingService.mapEntityToDto(bedRepository.save(bed));
    }

    public Bed getBedOrThrow(long id) {
        return bedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bed by id: " + id + " not found"));
    }

    @Override
    public BedDto getBedById(Long id) {
        Bed bed = getBedOrThrow(id);
        return bedMappingService.mapEntityToDto(bed);
    }

    @Override
    public List<BedDto> getAllBeds() {
        return bedRepository.findAll()
                .stream()
                .map(bedMappingService::mapEntityToDto)
                .toList();
    }

    @Override
    public void deleteBedById(Long id) {
        getBedOrThrow(id);
        bedRepository.deleteById(id);
    }

}
