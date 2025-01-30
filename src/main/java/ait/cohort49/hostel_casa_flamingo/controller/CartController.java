package ait.cohort49.hostel_casa_flamingo.controller;

import ait.cohort49.hostel_casa_flamingo.model.dto.CartDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.service.UserService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart controller", description = "Controller for operations with cart")

public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }


    @Operation(summary = "Get cart", tags = {"cart"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/xml", schema = @Schema(implementation = CartDto.class))),
            @ApiResponse(responseCode = "404", description = "cart not found", content = @Content)
    })
    /**
     * Получить корзину пользователя
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public CartDto getCart(@AuthenticationPrincipal String userEmail) {
        User user = userService.getUserByEmailOrThrow(userEmail);
        return cartService.getCart(user);
    }

    @Operation(summary = "Add bed to cart", description = "Add bed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class, example = "User not authenticated"))
            )
    })
    /**
     * Добавить кровать в корзину
     */
    @PostMapping("/bed/{bedId}")
    @PreAuthorize("isAuthenticated()")
    public void addBedToCart(@PathVariable Long bedId, @AuthenticationPrincipal String userEmail) {
        User user = userService.getUserByEmailOrThrow(userEmail);
        cartService.addBedToCart(user, bedId);
    }


    @Operation(summary = "Delete bed from cart", description = "Delete bed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class, example = "User not authenticated"))
            )
    })
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


    @Operation(summary = "Clear a cart", description = "Clear a cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartDto.class))
            ),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class, example = "User not authenticated"))
            )
    })
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
