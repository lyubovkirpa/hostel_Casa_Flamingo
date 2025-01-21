package ait.cohort49.hostel_casa_flamingo.controller;

import ait.cohort49.hostel_casa_flamingo.model.dto.CartDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.CartService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Получить корзину пользователя
    @GetMapping("")
    public CartDto getCart() {
        User user = findUserById();  // метод для поиска пользователя по ID
        return cartService.getCart(user);  // Преобразуем Cart в CartDto для передачи в ответе
    }

    // Добавить кровать в корзину
    @PostMapping("/bed/{bedId}")
    public void addBedToCart(@PathVariable Long bedId) {
        User user = findUserById();
        cartService.addBedToCart(user, bedId);
    }

    // Удалить кровать из корзины
    @DeleteMapping("/remove_bed/{bedId}")
    public void removeBedFromCart(@PathVariable Long bedId) {
        User user = findUserById();
        cartService.removeBedFromCart(user, bedId);
    }

    // Получить общую стоимость корзины
    @GetMapping("/total_price")
    public BigDecimal getTotalPrice() {
        User user = findUserById();
        return cartService.getTotalPrice(user);
    }

    // Очистить корзину
    @DeleteMapping("/clear")
    public void clearCart() {
        User user = findUserById();
        cartService.clearUserCart(user);
    }

    // Метод для поиска пользователя по ID
    private User findUserById() {
        // Здесь нужно реализовать поиск пользователя через UserRepository или сервис
        return new User();  // временно так )
    }
}
