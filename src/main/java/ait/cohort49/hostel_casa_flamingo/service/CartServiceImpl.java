package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.dto.CartDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.repository.CartRepository;
import ait.cohort49.hostel_casa_flamingo.security.service.UserService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BedService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.CartService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.CartMappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class CartServiceImpl implements CartService {

    private final BedService bedService;
    private final CartRepository cartRepository;
    private final CartMappingService cartMappingService;
    private final UserService userService;

    public CartServiceImpl(BedService bedService, CartRepository cartRepository, CartMappingService cartMappingService, UserService userService) {
        this.bedService = bedService;
        this.cartRepository = cartRepository;
        this.cartMappingService = cartMappingService;
        this.userService = userService;
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
    public void removeBedFromCart(User authUser, Long bedId) {

        Bed foundBed = bedService.getBedOrThrow(bedId);
        Cart userCart = getCartEntity(authUser);
        userCart.getBeds().remove(foundBed);
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
