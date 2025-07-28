package ca.uqam.mgl7361.lel.gp1.shop.presentation;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.uqam.mgl7361.lel.gp1.shop.business.CartService;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.CartBookRequest;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.CartDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.shop.exception.InvalidCartException;
import ca.uqam.mgl7361.lel.gp1.shop.exception.UnsufficientStockException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/cart")
@Tag(name = "Cart", description = "Endpoints for managing shopping carts")
public class CartController {

    private final CartService cartService;
    private static final Logger logger = LogManager.getLogger(CartController.class);

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(summary = "Get the cart of a user (by AccountDTO)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/view")
    public ResponseEntity<?> getCart(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User account object", required = true, content = @Content(schema = @Schema(implementation = AccountDTO.class))) @org.springframework.web.bind.annotation.RequestBody AccountDTO accountDto) {
        try {
            CartDTO cartDto = cartService.getCart(accountDto);
            return ResponseEntity.ok(cartDto);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Get the cart of a user by account ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/view/{accountId}")
    public ResponseEntity<?> getCart(
            @PathVariable(name = "accountId") @Parameter(description = "ID of the account") int accountId) {
        try {
            CartDTO cartDto = cartService.getCartFor(accountId);
            return ResponseEntity.ok(cartDto);
        } catch (Exception e) {
            logger.error("Error while getting cart for account id : " + accountId, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Add a book to a user's cart")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book added to cart"),
            @ApiResponse(responseCode = "400", description = "Invalid cart or book out of stock"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/add")
    public ResponseEntity<?> addBookToCart(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request containing account and book", required = true, content = @Content(schema = @Schema(implementation = CartBookRequest.class))) @org.springframework.web.bind.annotation.RequestBody CartBookRequest request) {
        try {
            cartService.addBookToCart(request.account(), request.book());
            return ResponseEntity.ok().body("Book added to cart successfully.");
        } catch (UnsufficientStockException e) {
            return ResponseEntity.badRequest().body("Book is not in stock.");
        } catch (InvalidCartException e) {
            return ResponseEntity.badRequest().body("Cart is invalid.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Error adding book to cart : " + e.getMessage()));
        }
    }

    @Operation(summary = "Remove a book from a user's cart")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book removed from cart"),
            @ApiResponse(responseCode = "400", description = "Invalid cart"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/remove")
    public ResponseEntity<?> removeBookFromCart(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Request containing account and book", required = true, content = @Content(schema = @Schema(implementation = CartBookRequest.class))) @org.springframework.web.bind.annotation.RequestBody CartBookRequest request) {
        try {
            cartService.removeBookFromCart(request.account(), request.book());
            return ResponseEntity.ok().body("Book deleted from cart successfully.");
        } catch (InvalidCartException e) {
            return ResponseEntity.badRequest().body("Invalid cart.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Error removing book from cart : " + e.getMessage()));
        }
    }

    @Operation(summary = "Clear a user's cart (by AccountDTO)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart cleared"),
            @ApiResponse(responseCode = "400", description = "Invalid or already empty cart"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping("/clear")
    public ResponseEntity<?> clearCart(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User account to clear cart for", required = true, content = @Content(schema = @Schema(implementation = AccountDTO.class))) @org.springframework.web.bind.annotation.RequestBody AccountDTO accountDto) {
        try {
            cartService.clearCart(accountDto);
            return ResponseEntity.ok().body("Cart emptied successfully ! (or was already empty)");
        } catch (InvalidCartException e) {
            return ResponseEntity.badRequest().body("Cart is invalid or already empty.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Error clearing cart : " + e.getMessage()));
        }
    }

    @Operation(summary = "Clear a user's cart by cart ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cart cleared"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @DeleteMapping("/clear/{id}")
    public ResponseEntity<?> clearCart(
            @PathVariable(name = "id") @Parameter(description = "Cart ID") int id) {
        try {
            cartService.clearCart(id);
            return ResponseEntity.ok().body("Cart emptied successfully ! (or was already empty)");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Error clearing cart : " + e.getMessage()));
        }
    }
}
