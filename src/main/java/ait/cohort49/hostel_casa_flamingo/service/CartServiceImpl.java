package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.dto.CartDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import ait.cohort49.hostel_casa_flamingo.model.entity.CartItemBed;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.repository.CartRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BedService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.CartService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.CartMappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


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

        CartItemBed newCartItemBed = new CartItemBed();
        newCartItemBed.setBed(foundBed);
        newCartItemBed.setCart(userCart);
        newCartItemBed.setEntryDate(ZonedDateTime.now());
        newCartItemBed.setDepartureDate(ZonedDateTime.now());

        userCart.getCartItemBeds().add(newCartItemBed);
        cartRepository.save(userCart);
    }

    @Override
    public void removeBedFromCart(User authUser, Long bedId) {
        Bed foundBed = bedService.getBedOrThrow(bedId);
        Cart userCart = getCartEntity(authUser);

        List<CartItemBed> cartItemBeds = userCart.getCartItemBeds();
        Optional<CartItemBed> itemBed = cartItemBeds.stream()
                .filter(cartItemBed -> cartItemBed.getBed().equals(foundBed))
                .findFirst();
        itemBed.ifPresent(cartItemBeds::remove);
        cartRepository.save(userCart);
    }

    @Override
    public BigDecimal getTotalPrice(User authUser) {

        Cart userCart = getCartEntity(authUser);

        return userCart.getCartItemBeds()
                .stream()
                .map(CartItemBed::getBed)
                .map(Bed::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void clearUserCart(User authUser) {
        Cart cartEntity = getCartEntity(authUser);
        cartEntity.getCartItemBeds().clear();

        cartRepository.save(cartEntity);
    }
}
