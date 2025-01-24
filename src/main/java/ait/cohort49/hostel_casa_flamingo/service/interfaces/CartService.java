package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.dto.CartDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;

import java.math.BigDecimal;

public interface CartService {

    CartDto getCart(User user);

    Cart getCartEntity(User authUser);

    void addBedToCart(User authUser, Long bedId);

    void addRoomToCart(User authUser, Long roomId);

    void removeBedFromCart(User authUser, Long bedId);

    void removeRoomFromCart(User authUser, Long roomId);

    BigDecimal getTotalPrice(User authUser);

    void clearUserCart(User authUser);
}
