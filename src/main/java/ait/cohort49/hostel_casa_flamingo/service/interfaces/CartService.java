package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;

import java.math.BigDecimal;

public interface CartService {

    public Cart getCart(User user);

    public void addBedToCart(Cart cart, Bed bed);

    public void removeBedFromCart(Cart cart, Long id);

    public BigDecimal getTotalPrice(Cart cart);

    public void clearCart(Cart cart);


}
