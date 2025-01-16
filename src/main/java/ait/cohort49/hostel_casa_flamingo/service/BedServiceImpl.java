package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.dto.BedDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.repository.BedRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BedService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.BedMappingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
public class BedServiceImpl implements BedService {

    private final BedRepository bedRepository;
    private final BedMappingService bedMappingService;

    public BedServiceImpl(BedRepository bedRepository, BedMappingService bedMappingService) {
        this.bedRepository = bedRepository;
        this.bedMappingService = bedMappingService;
    }

    @Override
    public BedDto saveBed(BedDto bedDto) {
        Bed bed = bedMappingService.mapDtoToEntity(bedDto);
        return bedMappingService.mapEntityToDto(bedRepository.save(bed));
    }

    @Override
    public BedDto getBedById(Long id) {
        Bed bed = bedRepository.findById(id).orElse(null);
        return bedMappingService.mapEntityToDto(Objects.requireNonNull(bed));
    }

    @Override
    public List<BedDto> getAllBeds() {
        return bedRepository.findAll()
                .stream()
                .map(bedMappingService::mapEntityToDto).toList();
    }


    @Override
    public void deleteBedById(Long id) {
        Bed bed = bedRepository.findById(id).orElse(null);
        if (bed == null) {
            throw new RuntimeException("Bed with id " + id + " not found");
        }
        bedRepository.deleteById(id);
    }

}
