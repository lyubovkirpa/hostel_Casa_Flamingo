package ait.cohort49.hostel_casa_flamingo.controller;

import ait.cohort49.hostel_casa_flamingo.model.dto.CartDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.CartService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.CartMappingService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final CartMappingService cartMappingService;

    public CartController(CartService cartService, CartMappingService cartMappingService) {
        this.cartService = cartService;
        this.cartMappingService = cartMappingService;
    }

    // Получить корзину пользователя
    @GetMapping("/{userId}")
    public CartDto getCart(@PathVariable Long userId) {
        User user = findUserById(userId);  // метод для поиска пользователя по ID
        Cart cart = cartService.getCart(user);
        return cartMappingService.mapEntityToDto(cart);  // Преобразуем Cart в CartDto для передачи в ответе
    }

    // Добавить кровать в корзину
    @PostMapping("/{userId}/bed")
    public void addBedToCart(@PathVariable Long userId, @RequestBody Bed bed) {
        User user = findUserById(userId);
        Cart cart = cartService.getCart(user);
        cartService.addBedToCart(cart, bed);
    }

    // Удалить кровать из корзины
    @DeleteMapping("/{userId}/remove_bed/{bedId}")
    public void removeBedFromCart(@PathVariable Long userId, @PathVariable Long bedId) {
        User user = findUserById(userId);
        Cart cart = cartService.getCart(user);
        cartService.removeBedFromCart(cart, bedId);
    }

    // Получить общую стоимость корзины
    @GetMapping("/{userId}/total_price")
    public BigDecimal getTotalPrice(@PathVariable Long userId) {
        User user = findUserById(userId);
        Cart cart = cartService.getCart(user);
        return cartService.getTotalPrice(cart);
    }

    // Очистить корзину
    @DeleteMapping("/{userId}/clear")
    public void clearCart(@PathVariable Long userId) {
        User user = findUserById(userId);
        Cart cart = cartService.getCart(user);
        cartService.clearCart(cart);
    }

    // Метод для поиска пользователя по ID
    private User findUserById(Long userId) {
        // Здесь нужно реализовать поиск пользователя через UserRepository или сервис
        return new User();  // временно так )
    }
}


