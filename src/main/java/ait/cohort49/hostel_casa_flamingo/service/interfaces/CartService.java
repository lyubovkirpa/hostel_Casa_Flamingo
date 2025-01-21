package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;

import java.math.BigDecimal;

public interface CartService {

    Cart getCart(User user);

    void addBedToCart(Cart cart, Bed bed);

    void removeBedFromCart(Cart cart, Long id);

    BigDecimal getTotalPrice(Cart cart);

    void clearCart(Cart cart);


}
