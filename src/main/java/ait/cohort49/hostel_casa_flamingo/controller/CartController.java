package ait.cohort49.hostel_casa_flamingo.controller;

import ait.cohort49.hostel_casa_flamingo.model.dto.CartDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.repository.UserRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.CartService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    public CartController(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    // Получить корзину пользователя
    @GetMapping("/{userId}")
    public CartDto getCart(@PathVariable Long userId) {
        User user = findUserById(userId);
        return cartService.getCart(user);  // Преобразуем Cart в CartDto для передачи в ответе
    }

    // Добавить кровать в корзину
    @PostMapping("/bed/{bedId}")
    public void addBedToCart(@PathVariable Long bedId, @RequestParam Long userId) {
        User user = findUserById(userId);
        cartService.addBedToCart(user, bedId);
    }

    // Удалить кровать из корзины
    @DeleteMapping("/remove_bed/{bedId}")
    public void removeBedFromCart(@PathVariable Long bedId, @RequestParam Long userId) {
        User user = findUserById(userId);
        cartService.removeBedFromCart(user, bedId);
    }

    // Получить общую стоимость корзины
    @GetMapping("/total_price")
    public BigDecimal getTotalPrice(@RequestParam Long userId) {
        User user = findUserById(userId);
        return cartService.getTotalPrice(user);
    }

    // Очистить корзину
    @DeleteMapping("/clear")
    public void clearCart(@RequestParam Long userId) {
        User user = findUserById(userId);
        cartService.clearUserCart(user);
    }

    // Метод для поиска пользователя по ID
    private User findUserById(Long userId) {
return userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));

    }
}
