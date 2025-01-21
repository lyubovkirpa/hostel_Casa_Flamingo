package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.CartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class CartServiceImpl implements CartService {
    @Override
    public Cart getCart(User user) {
        return null;
//        return user.getCart();
    }

    @Override
    public void addBedToCart(Cart cart, Bed bed) {
        if (cart != null && bed != null) {
            cart.addBed(bed);
        }
    }

    @Override
    public void removeBedFromCart(Cart cart, Long id) {
        if (cart != null) {
            cart.removeById(id);
        }
    }

    @Override
    public BigDecimal getTotalPrice(Cart cart) {
        if (cart != null) {
            return cart.getTotalPrice();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public void clearCart(Cart cart) {
        if (cart != null) {
            cart.clear();
        }
    }
}
