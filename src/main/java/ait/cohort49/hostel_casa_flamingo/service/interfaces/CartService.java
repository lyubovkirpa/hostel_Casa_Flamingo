package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.dto.CartDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;

import java.math.BigDecimal;

public interface CartService {

    CartDto getCart(User user);

    Cart getCartEntity(User authUser);

    void addBedToCart(User authUser, Long bedId);

    void removeBedFromCart(User authUser, Long bedId);

    BigDecimal getTotalPrice(User authUser);

    void clearUserCart(User authUser);
}
