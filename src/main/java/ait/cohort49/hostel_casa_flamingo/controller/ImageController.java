package ait.cohort49.hostel_casa_flamingo.controller;

import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Image;
import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import ait.cohort49.hostel_casa_flamingo.service.BedServiceImpl;
import ait.cohort49.hostel_casa_flamingo.service.RoomServiceImpl;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;
    private final BedServiceImpl bedService;
    private final RoomServiceImpl roomService;

    public ImageController(ImageService imageService, BedServiceImpl bedService, RoomServiceImpl roomService) {
        this.imageService = imageService;
        this.bedService = bedService;
        this.roomService = roomService;
    }

    /**
     * Загрузка изображения для кровати (Bed)
     */
    @PostMapping("/upload/bed/{bedId}")
    public String uploadImageForBed(@PathVariable Long bedId,
                                    @RequestParam("file") MultipartFile file) {
        Bed bed = bedService.getBedOrThrow(bedId);
        return imageService.uploadImageForBed(file, bed);  // Вернем URL изображения
    }

    /**
     * Загрузка изображения для комнаты (Room)
     */
    @PostMapping("/upload/room/{roomId}")
    public String uploadImageForRoom(@PathVariable Long roomId,
                                     @RequestParam("file") MultipartFile file) {
        Room room = roomService.findByIdOrThrow(roomId);
        return imageService.uploadImageForRoom(file, room);  // Вернем URL изображения
    }

    /**
     * Получение изображений для кровати
     */
    @GetMapping("/bed/{bedId}")
    public List<Image> getImagesByBed(@PathVariable Long bedId) {
        return imageService.getImagesByBed(bedId);  // Возвращаем список изображений
    }

    /**
     * Получение изображений для комнаты
     */
    @GetMapping("/room/{roomId}")
    public List<Image> getImagesByRoom(@PathVariable Long roomId) {
        return imageService.getImagesByRoom(roomId);  // Возвращаем список изображений
    }

    /**
     * Удаление изображения по ID
     */
    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);  // Удаляем изображение
    }

    /**
     * Получение Presigned URL для изображения по ID
     */
    @GetMapping("/presigned/{imageId}")
    public String getPresignedUrl(@PathVariable Long imageId) {
        URL presignedUrl = imageService.getPresignedUrl(imageId);
        return presignedUrl.toString();
    }
}
