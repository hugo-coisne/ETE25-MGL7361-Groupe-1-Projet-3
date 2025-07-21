package ca.uqam.mgl7361.lel.gp1.common.dtos.user;

import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;

public record CartItemDTO(
        BookDTO book,
        int quantity) {
}