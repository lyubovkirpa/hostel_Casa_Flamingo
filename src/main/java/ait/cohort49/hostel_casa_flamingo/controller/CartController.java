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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart", description = "Controller for operations with cart")

public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }


    @Operation(summary = "Get user's cart", description = "Retrieve the cart for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CartDto.class)),
                            @Content(mediaType = "application/xml",
                                    schema = @Schema(implementation = CartDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "User not authenticated"))
            ),
            @ApiResponse(responseCode = "404", description = "Cart not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "Cart not found"))
            )
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

    @Operation(summary = "Add bed to cart", description = "Add a bed to the authenticated user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bed successfully added",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "User not authenticated"))
            ),
            @ApiResponse(responseCode = "404", description = "Bed or cart not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "Resource not found"))
            )
    })
    /**
     * Добавить кровать в корзину
     */
    @PostMapping("/bed/{bedId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addBedToCart(@PathVariable Long bedId, @AuthenticationPrincipal String userEmail) {
        User user = userService.getUserByEmailOrThrow(userEmail);
        cartService.addBedToCart(user, bedId);
    }


    @Operation(summary = "Remove bed from cart", description = "Remove a bed from the authenticated user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Bed successfully removed",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "User not authenticated"))
            ),
            @ApiResponse(responseCode = "404", description = "Bed or cart not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "Resource not found"))
            )
    })
    /**
     * Удалить кровать из корзины
     */
    @DeleteMapping("/remove_bed/{bedId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeBedFromCart(@PathVariable Long bedId, @AuthenticationPrincipal String userEmail) {
        User user = userService.getUserByEmailOrThrow(userEmail);
        cartService.removeBedFromCart(user, bedId);
    }


    @Operation(summary = "Get total price", description = "Get the total price of all beds in the authenticated user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(type = "number", example = "120.99")),
                            @Content(mediaType = "application/xml",
                                    schema = @Schema(type = "number", example = "120.99"))
                    }
            ),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "User not authenticated"))
            ),
            @ApiResponse(responseCode = "404", description = "Cart not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "Cart not found"))
            )
    })
    /**
     * Получить общую стоимость корзины
     */
    @GetMapping("/total_price")
    @PreAuthorize("isAuthenticated()")
    public BigDecimal getTotalPrice(@AuthenticationPrincipal String userEmail) {
        User user = userService.getUserByEmailOrThrow(userEmail);
        return cartService.getTotalPrice(user);
    }


    @Operation(summary = "Clear the cart", description = "Completely clears the authenticated user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cart successfully cleared",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "User not authenticated"))
            ),
            @ApiResponse(responseCode = "404", description = "Cart not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "Cart not found"))
            )
    })
    /**
     * Очистить корзину
     */
    @DeleteMapping("/clear")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(@AuthenticationPrincipal String userEmail) {
        User user = userService.getUserByEmailOrThrow(userEmail);
        cartService.clearUserCart(user);
    }
}
