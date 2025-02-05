package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.exception.RestException;
import ait.cohort49.hostel_casa_flamingo.model.dto.BedDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.CreateBedDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import ait.cohort49.hostel_casa_flamingo.repository.BedRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BedService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.RoomService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.BedMappingService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class BedServiceImpl implements BedService {

    private final BedRepository bedRepository;
    private final BedMappingService bedMappingService;
    private final RoomService roomService;


    public BedServiceImpl(BedRepository bedRepository, BedMappingService bedMappingService, RoomService roomService) {
        this.bedRepository = bedRepository;
        this.bedMappingService = bedMappingService;
        this.roomService = roomService;
    }

    @Override
    public BedDto saveBed(CreateBedDto createBedDto) {
        Bed bed = bedMappingService.mapDtoToEntity(createBedDto);
        Room room = roomService.findByIdOrThrow(createBedDto.getRoomId());
        bed.setRoom(room);
        return bedMappingService.mapEntityToDto(bedRepository.save(bed));
    }

    @Override
    public Bed getBedOrThrow(long id) {
        return bedRepository.findById(id)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Bed by id: " + id + " not found"));
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
