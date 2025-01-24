package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.dto.CartDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.repository.CartRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BedService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.CartService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.RoomService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.CartMappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class CartServiceImpl implements CartService {

    private final BedService bedService;
    private final RoomService roomService;
    private final CartRepository cartRepository;
    private final CartMappingService cartMappingService;

    public CartServiceImpl(BedService bedService, RoomService roomService, CartRepository cartRepository, CartMappingService cartMappingService) {
        this.bedService = bedService;
        this.roomService = roomService;
        this.cartRepository = cartRepository;
        this.cartMappingService = cartMappingService;
    }

    @Override
    public CartDto getCart(User authUser) {
        return cartMappingService.mapEntityToDto(getCartEntity(authUser));
    }

    @Override
    public Cart getCartEntity(User authUser) {
        return authUser.getCart();
    }

    @Override
    public void addBedToCart(User authUser, Long bedId) {

        Bed foundBed = bedService.getBedOrThrow(bedId);
        Cart userCart = getCartEntity(authUser);

        if (!userCart.getBeds().contains(foundBed)) {
            userCart.getBeds().add(foundBed);
            cartRepository.save(userCart);
        }
    }

    @Override
    public void addRoomToCart(User authUser, Long roomId) {
        Room foundRoom = roomService.findByIdOrThrow(roomId);
        Cart userCart = getCartEntity(authUser);

        if (!userCart.getRooms().contains(foundRoom)) {
            userCart.getRooms().add(foundRoom);
            cartRepository.save(userCart);
        }
    }

    @Override
    public void removeBedFromCart(User authUser, Long bedId) {

        Bed foundBed = bedService.getBedOrThrow(bedId);
        Cart userCart = getCartEntity(authUser);
        userCart.getBeds().remove(foundBed);
        cartRepository.save(userCart);
    }

    @Override
    public void removeRoomFromCart(User authUser, Long roomId) {
        Room foundRoom = roomService.findByIdOrThrow(roomId);
        Cart userCart = getCartEntity(authUser);
        userCart.getRooms().remove(foundRoom);
        cartRepository.save(userCart);
    }

    @Override
    public BigDecimal getTotalPrice(User authUser) {

        Cart userCart = getCartEntity(authUser);

        return userCart.getBeds()
                .stream()
                .map(Bed::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void clearUserCart(User authUser) {
        Cart cartEntity = getCartEntity(authUser);
        cartEntity.getBeds().clear();

        cartRepository.save(cartEntity);
    }
}
