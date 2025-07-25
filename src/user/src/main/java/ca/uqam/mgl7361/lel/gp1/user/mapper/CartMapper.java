package ca.uqam.mgl7361.lel.gp1.user.mapper;

import java.util.stream.Collectors;

import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.CartItemDTO;
import ca.uqam.mgl7361.lel.gp1.user.model.Cart;

public class CartMapper {

    public static CartDTO toDTO(Cart cart) {
        if (cart == null) {
            return null;
        }
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setTotalPrice(cart.getTotalPrice());
        cartDTO.setCartItemDtos(cart.getBooks().entrySet().stream()
                .map(entry -> new CartItemDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList()));
        return cartDTO;
    }

    public static Cart toModel(CartDTO cartDTO) {
        if (cartDTO == null) {
            return null;
        }
        Cart cart = new Cart();
        cart.setId(cartDTO.getId());
        cart.setBooksDto(cartDTO.getCartItemDtos().stream()
                .collect(Collectors.toMap(
                        item -> item.book(), // Convert BookDTO to Book
                        CartItemDTO::quantity)));
        cart.setTotalPrice(cartDTO.getTotalPrice());
        return cart;
    }

}
