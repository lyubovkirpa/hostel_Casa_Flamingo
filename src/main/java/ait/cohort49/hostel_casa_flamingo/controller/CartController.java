package ait.cohort49.hostel_casa_flamingo.controller;

import ait.cohort49.hostel_casa_flamingo.model.dto.CartDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.CartService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    /**
     * Получить корзину пользователя
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public CartDto getCart(@AuthenticationPrincipal String userEmail) {
        User user = userService.getUserByEmailOrThrow(userEmail);
        return cartService.getCart(user);
    }


    /**
     * Добавить кровать в корзину
     */
    @PostMapping("/bed/{bedId}")
    @PreAuthorize("isAuthenticated()")
    public void addBedToCart(@PathVariable Long bedId, @AuthenticationPrincipal String userEmail) {
        User user = userService.getUserByEmailOrThrow(userEmail);
        cartService.addBedToCart(user, bedId);
    }

    /**
     * Удалить кровать из корзины
     */
    @DeleteMapping("/remove_bed/{bedId}")
    @PreAuthorize("isAuthenticated()")
    public void removeBedFromCart(@PathVariable Long bedId, @AuthenticationPrincipal String userEmail) {
        User user = userService.getUserByEmailOrThrow(userEmail);
        cartService.removeBedFromCart(user, bedId);
    }


    /**
     * Получить общую стоимость корзины
     */
    @GetMapping("/total_price")
    @PreAuthorize("isAuthenticated()")
    public BigDecimal getTotalPrice(@AuthenticationPrincipal String userEmail) {
        User user = userService.getUserByEmailOrThrow(userEmail);
        return cartService.getTotalPrice(user);
    }

    /**
     * Очистить корзину
     */
    @DeleteMapping("/clear")
    @PreAuthorize("isAuthenticated()")
    public void clearCart(@AuthenticationPrincipal String userEmail) {
        User user = userService.getUserByEmailOrThrow(userEmail);
        cartService.clearUserCart(user);
    }
}
