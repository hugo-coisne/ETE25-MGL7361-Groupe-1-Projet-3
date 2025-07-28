package ca.uqam.mgl7361.lel.gp1.common.dtos.shop;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Item in the shopping cart representing a book and its quantity")
public record CartItemDTO(
                @Schema(description = "The book in the cart", required = true) BookDTO book,

                @Schema(description = "Quantity of the book", example = "2", required = true) int quantity) {

        @Override
        public String toString() {
                return "CartItemDTO{bookDto=" + book + ", quantity=" + quantity + "}";
        }
}