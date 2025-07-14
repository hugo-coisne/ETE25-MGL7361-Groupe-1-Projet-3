package ca.uqam.mgl7361.lel.gp1.account.presentation.api.impl;

import java.util.logging.Logger;

import ca.uqam.mgl7361.lel.gp1.account.business.CartService;
import ca.uqam.mgl7361.lel.gp1.account.dto.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.account.dto.CartDTO;
import ca.uqam.mgl7361.lel.gp1.account.exception.InvalidCartException;
import ca.uqam.mgl7361.lel.gp1.account.exception.UnsufficientStockException;
import ca.uqam.mgl7361.lel.gp1.account.presentation.api.CartAPI;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.dtos.shop.BookDTO;

public class CartAPIImpl implements CartAPI {

    private static CartService cartService = CartService.getInstance();

    static Logger logger = Logger.getLogger(CartAPIImpl.class.getName());

    public CartAPIImpl() {
    }

    public CartDTO getCart(AccountDTO accountDto) {
        logger.info("Retrieving cart for account: " + accountDto.getEmail());
        CartDTO cartDto = cartService.getCart(accountDto);
        logger.info("Cart retrieved for account: " + accountDto.getEmail() + ", Cart ID: " + cartDto.getId()
                + ", Total Price: " + cartDto.getTotalPrice());
        return cartDto;
    }

    @Override
    public void add(BookDTO bookDto, AccountDTO accountDto) {
        try {
            cartService.addBookToCart(accountDto, bookDto);
            System.out.println("Livre ajouté au panier avec succès !");
        } catch (UnsufficientStockException e) {
            System.out.println("Ce livre n'est pas en stock. Veuillez réessayer plus tard.");
        } catch (InvalidCartException e) {
            System.out.println("Le panier est invalide. Veuillez réessayer plus tard.");
            logger.severe("Invalid cart operation: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Une erreur s'est produite lors de l'ajout du livre au panier.");
            logger.severe("Error adding book to cart: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void remove(BookDTO bookDto, AccountDTO accountDto) {
        try {
            cartService.removeBookFromCart(accountDto, bookDto);
        } catch (InvalidCartException e) {
            System.out.println(
                    "Le panier est invalide ou ne contient pas le livre à retirer. Veuillez retirer un livre contenu dans le panier.");
            logger.severe("Invalid cart operation: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Une erreur s'est produite lors de la suppression du livre du panier.");
            logger.severe("Error removing book from cart: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void clearCart(AccountDTO accountDto) {
        try {
            cartService.clearCart(accountDto);
            System.out.println("Panier vidé avec succès !");
        } catch (InvalidCartException e) {
            System.out.println("Le panier est invalide ou est déjà vide.");
            logger.severe("Invalid cart operation: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Une erreur s'est produite lors de la vidange du panier.");
            logger.severe("Error clearing cart: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
