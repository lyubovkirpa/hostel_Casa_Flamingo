package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.exception.RestException;
import ait.cohort49.hostel_casa_flamingo.model.dto.CartDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import ait.cohort49.hostel_casa_flamingo.model.entity.CartItemBed;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.repository.CartItemBedRepository;
import ait.cohort49.hostel_casa_flamingo.repository.CartRepository;
import ait.cohort49.hostel_casa_flamingo.repository.RoomRepository;
import ait.cohort49.hostel_casa_flamingo.repository.UserRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BedService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.CartService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.CartMappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class CartServiceImpl implements CartService {

    private final BedService bedService;
    private final CartRepository cartRepository;
    private final CartMappingService cartMappingService;
    private final UserRepository userRepository;
    private final CartItemBedRepository cartItemBedRepository;
    private final RoomRepository roomRepository;

    public CartServiceImpl(BedService bedService, CartRepository cartRepository, CartMappingService cartMappingService, UserRepository userRepository, CartItemBedRepository cartItemBedRepository, RoomRepository roomRepository) {
        this.bedService = bedService;
        this.cartRepository = cartRepository;
        this.cartMappingService = cartMappingService;
        this.userRepository = userRepository;
        this.cartItemBedRepository = cartItemBedRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public CartDto getCart(User authUser) {
        Cart userCart = getCartEntity(authUser);
        CartDto cartDto = cartMappingService.mapEntityToDto(userCart);

        long countBeds = userCart.getCartItemBeds().size();
        cartDto.setCountBeds(countBeds);

        BigDecimal totalPriceBeds = userCart.getCartItemBeds()
                .stream()
                .map(cartItemBed -> cartItemBed.getBed().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cartDto.setTotalPriceBeds((totalPriceBeds));

        return cartDto;
    }

    @Override
    public Cart getCartEntity(User authUser) {
        return cartRepository.findByUser(authUser)
                .orElseGet(() -> {
                            Cart userCart = new Cart();
                            userCart.setUser(authUser);
                            cartRepository.save(userCart);
                            authUser.setCart(userCart);
                            userRepository.save(authUser);
                            return userCart;
                        }
                );
    }

    @Override
    public void addBedToCart(User authUser, Long bedId, LocalDate entryDate, LocalDate departureDate) {

        Bed foundBed = bedService.getBedOrThrow(bedId);
        Cart userCart = getCartEntity(authUser);

        Optional<CartItemBed> existingCartItem = userCart.getCartItemBeds()
                .stream()
                .filter(cartItemBed -> cartItemBed.getBed().equals(foundBed) &&
                        cartItemBed.getEntryDate().equals(entryDate) &&
                        cartItemBed.getDepartureDate().equals(departureDate))
                .findFirst();

        if (existingCartItem.isPresent()) {
            throw new RestException("The bed with id " + foundBed.getId() + " for the dates from " + entryDate + " to " + departureDate + " is already in the cart.");
        }

        CartItemBed newCartItemBed = new CartItemBed();
        newCartItemBed.setBed(foundBed);
        newCartItemBed.setCart(userCart);

        newCartItemBed.setEntryDate(entryDate);
        newCartItemBed.setDepartureDate(departureDate);

        cartItemBedRepository.save(newCartItemBed);
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

    @Override
    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }

    @Override
    public boolean isBedInCart(Long bedId) {
        return cartItemBedRepository.existsByBedId(bedId);
    }
}
