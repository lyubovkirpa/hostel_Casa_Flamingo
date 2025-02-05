package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.exception.RestException;
import ait.cohort49.hostel_casa_flamingo.model.dto.CreateOrUpdateRoomDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.RoomDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import ait.cohort49.hostel_casa_flamingo.repository.BedRepository;
import ait.cohort49.hostel_casa_flamingo.repository.CartItemBedRepository;
import ait.cohort49.hostel_casa_flamingo.repository.RoomRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.RoomService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.RoomMappingService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMappingService roomMappingService;
    private final CartItemBedRepository cartItemBedRepository;
    private final BedRepository bedRepository;

    public RoomServiceImpl(RoomRepository roomRepository, RoomMappingService roomMappingService, CartItemBedRepository cartItemBedRepository, BedRepository bedRepository) {
        this.roomRepository = roomRepository;
        this.roomMappingService = roomMappingService;
        this.cartItemBedRepository = cartItemBedRepository;
        this.bedRepository = bedRepository;
    }

    @Override
    public RoomDto getRoomById(Long id) {
        Room room = findByIdOrThrow(id);
        return createDtoWithTotalPrice(room);
    }

    private RoomDto createDtoWithTotalPrice(Room room) {
        BigDecimal priceRoom = room.getBeds()
                .stream()
                .map(Bed::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        RoomDto roomDto = roomMappingService.mapEntityToDto(room);
        roomDto.setPrice(priceRoom);
        return roomDto;
    }

    @Override
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(this::createDtoWithTotalPrice)
                .toList();
    }

    @Override
    public RoomDto createRoom(CreateOrUpdateRoomDto createRoomDto) {
        Room room = roomMappingService.mapDtoToEntity(createRoomDto);
        return roomMappingService.mapEntityToDto(roomRepository.save(room));
    }


    @Override
    @Transactional
    public void deleteRoom(Long id) {
        Room room = findByIdOrThrow(id);

        //        TODO
//        1. Достать все кровати найденной комнаты
//        2. Проверить есть ли указанные комнаты в Cart, убедиться в том пытается ли
//        их ктото забронировать
//        3. Если пытается ктото забронировать -выкинуть ошибку, что ее пытается ктото забронировать
//        4. Проделать шаг 2,3 и для Booking, n е выкинуть ошибку, в случае если кровати в комнате
//        забронированы на сегодня или будущее в Booking. Если подтверждение было в прошлом,
//        удалить все связанные  Booking для каждой кровати в этой комнате, затем удалить саму кровать
//        написать в CartService метод, который будет проверять наличие кровати в табл Cart
//        в BookingService должен быть метод(есть ли кровати, которые подтверждены на сегодгня
//        или будущее-> шаг 4; если метод вернет false -> обращаться к другому методу, который вернет список
//        бронирований прошлого по выбранной кровати->каждое бронировнаие из списка удалить


        for (Bed bed : room.getBeds()) {
            cartItemBedRepository.deleteBedById(bed.getId());
        }

        for (Bed bed : room.getBeds()) {
            bedRepository.delete(bed);
        }

        roomRepository.delete(room);
    }

    @Override
    public Room findByIdOrThrow(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Room by id: " + id + " not found"));
    }

    @Override
    public BigDecimal getTotalBedPriceForRoom(Long roomId) {
        Room selectedRoom = findByIdOrThrow(roomId);
        return getTotalBedPriceForRoom(selectedRoom);
    }

    @Override
    public BigDecimal getTotalBedPriceForRoom(Room room) {
        return room.getBeds()
                .stream()
                .map(Bed::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
