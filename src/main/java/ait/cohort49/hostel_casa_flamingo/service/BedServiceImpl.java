package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.exception.RestException;
import ait.cohort49.hostel_casa_flamingo.model.dto.BedDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.CreateBedDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Image;
import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import ait.cohort49.hostel_casa_flamingo.repository.BedRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BedService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.RoomService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.S3StorageService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.BedMappingService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class BedServiceImpl implements BedService {

    private final BedRepository bedRepository;
    private final BedMappingService bedMappingService;
    private final RoomService roomService;
    private final S3StorageService s3StorageService;

    public BedServiceImpl(BedRepository bedRepository,
                          BedMappingService bedMappingService,
                          RoomService roomService,
                          S3StorageService s3StorageService) {
        this.bedRepository = bedRepository;
        this.bedMappingService = bedMappingService;
        this.roomService = roomService;
        this.s3StorageService = s3StorageService;
    }

    @Override
    public BedDto saveBed(CreateBedDto createBedDto) {
        Bed bed = bedMappingService.mapDtoToEntity(createBedDto);
        Room room = roomService.findByIdOrThrow(createBedDto.getRoomId());
        bed.setRoom(room);

        if (bedRepository.existsByRoomIdAndNumber(room.getId(), bed.getNumber())) {
            throw new RestException(HttpStatus.CONFLICT, "Bed number already exists in this room");
        }
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

        List<Image> bedImages = (bed.getImages() != null) ? bed.getImages() : new ArrayList<>();

        List<String> bedImagesUrls = !bedImages.isEmpty()
                ? generateImageUrls(bedImages)
                : new ArrayList<>();  // Пустой список, если нет изображений

        BedDto bedDto = bedMappingService.mapEntityToDto(bed);
        bedDto.setImageUrls(bedImagesUrls);
        return bedDto;
    }

    private List<String> generateImageUrls(List<Image> bedImages) {
        return bedImages.stream()
                .map(image -> s3StorageService.generatePresignedUrl(image.getS3Path()).toString())
//                .map(uri -> uri.toString())
                .toList();
    }

    @Override
    public List<BedDto> getAllBeds() {
        List<Bed> beds = bedRepository.findAll();
        List<BedDto> bedDtos = new ArrayList<>();
        for (Bed bed : beds) {
            List<Image> bedImages = bed.getImages();
            List<String> bedImagesUrls = generateImageUrls(bedImages);
            BedDto bedDto = bedMappingService.mapEntityToDto(bed);
            bedDto.setImageUrls(bedImagesUrls);
            bedDtos.add(bedDto);
        }
        return bedDtos;
    }

    @Override
    public void deleteBedById(Long id) {
        getBedOrThrow(id);
        bedRepository.deleteById(id);
    }

    @Override
    public List<BedDto> getAvailableBeds(LocalDate entryDate, LocalDate departureDate) {
        List<Bed> availableBeds = bedRepository.findAvailableBeds(entryDate, departureDate);
        List<BedDto> availableBedDtos = new ArrayList<>();
        for (Bed bed : availableBeds) {
            List<Image> bedImages = bed.getImages();
            List<String> bedImagesUrls = generateImageUrls(bedImages);
            BedDto bedDto = bedMappingService.mapEntityToDto(bed);
            bedDto.setImageUrls(bedImagesUrls);
        }
        return availableBedDtos;
    }
}
