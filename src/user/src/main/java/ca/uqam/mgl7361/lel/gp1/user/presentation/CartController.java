package ca.uqam.mgl7361.lel.gp1.user.presentation;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.uqam.mgl7361.lel.gp1.user.business.CartService;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartDTO;
import ca.uqam.mgl7361.lel.gp1.user.exception.InvalidCartException;
import ca.uqam.mgl7361.lel.gp1.user.exception.InvalidCredentialsException;
import ca.uqam.mgl7361.lel.gp1.user.exception.UnsufficientStockException;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private static final Logger logger = LogManager.getLogger(CartController.class);

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/view")
    public ResponseEntity<?> getCart(@RequestBody AccountDTO accountDto) {
        logger.info("Received request for : " + accountDto);
        CartDTO cartDto;
        try {
            cartDto = cartService.getCart(accountDto);
            logger.debug("Cart retrieved for account: {}, Cart ID: {}, Total Price: {}",
                    accountDto.getEmail(), cartDto.getId(), cartDto.getTotalPrice());
            return ResponseEntity.ok(cartDto);
        } catch (InvalidCredentialsException e) {
            logger.error("Invalid credentials for account: " + accountDto.getEmail(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }
    }

    @GetMapping("/view/{accountId}")
    public ResponseEntity<?> getCart(@PathVariable(name = "accountId") int accountId) {
        logger.info("Received request for : " + accountId);
        CartDTO cartDto;
        try {
            cartDto = cartService.getCartFor(accountId);
            logger.debug("Cart retrieved for account id : {}", accountId);
            return ResponseEntity.ok(cartDto);
        } catch (Exception e) {
            logger.error("Error while getting cart for account id : " + accountId, e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBookToCart(@RequestBody CartBookRequest request) {
        logger.info("Received request " + request);
        try {
            cartService.addBookToCart(request.account(), request.book());
            return ResponseEntity.ok().body("Book added to cart successfully.");
        } catch (UnsufficientStockException e) {
            return ResponseEntity.badRequest().body("Book is not in stock.");
        } catch (InvalidCartException e) {
            logger.error("Invalid cart: " + e.getMessage());
            return ResponseEntity.badRequest().body("Cart is invalid.");
        } catch (InvalidCredentialsException e) {
            logger.error("Invalid credentials for account: " + request.account().getEmail(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        } catch (Exception e) {
            logger.error("Error adding book to cart", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Error adding book to cart : " + e.getMessage()));
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeBookFromCart(@RequestBody CartBookRequest request) {
        try {
            logger.info("Received request " + request);
            cartService.removeBookFromCart(request.account(), request.book());
            return ResponseEntity.ok().body("Book deleted from cart successfully.");
        } catch (InvalidCartException e) {
            logger.error("Invalid cart: " + e.getMessage());
            return ResponseEntity.badRequest().body("Invalid cart.");
        } catch (InvalidCredentialsException e) {
            logger.error("Invalid credentials for account: " + request.account().getEmail(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        } catch (Exception e) {
            logger.error("Error removing book from cart", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Error removing book from cart : " + e.getMessage()));
        }
    }

    @PostMapping("/clear")
    public ResponseEntity<?> clearCart(@RequestBody AccountDTO accountDto) {
        logger.info("Received request for " + accountDto);
        try {
            cartService.clearCart(accountDto);
            return ResponseEntity.ok().body("Cart emptied successfully ! (or was already empty)");
        } catch (InvalidCredentialsException e) {
            logger.error("Invalid credentials for account: " + accountDto.getEmail(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        } catch (InvalidCartException e) {
            logger.error("Invalid cart: " + e.getMessage());
            return ResponseEntity.badRequest().body("Cart is invalid or already empty.");
        } catch (Exception e) {
            logger.error("Error clearing cart", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Error clearing cart : " + e.getMessage()));
        }
    }

    @DeleteMapping("/clear/{id}")
    public ResponseEntity<?> clearCart(@PathVariable(name = "id") int id) {
        logger.info("Received request for cart id " + id);
        try {
            cartService.clearCart(id);
            return ResponseEntity.ok().body("Cart emptied successfully ! (or was already empty)");
        } catch (Exception e) {
            logger.error("Error clearing cart", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Error clearing cart : " + e.getMessage()));
        }
    }

    /**
     * Simple DTO to wrap book and account together.
     */
    public record CartBookRequest(AccountDTO account, BookDTO book) {
        public String toString() {
            return "CartBookRequest(AccountDTO=" + account + ", BookDTO=" + book + ")";
        }
    }
}