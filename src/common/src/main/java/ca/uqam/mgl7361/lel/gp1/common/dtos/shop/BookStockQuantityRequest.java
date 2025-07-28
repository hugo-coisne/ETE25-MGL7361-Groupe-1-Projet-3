package ca.uqam.mgl7361.lel.gp1.common.dtos.shop;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object to check or update book stock with quantity")
public record BookStockQuantityRequest(

        @Schema(description = "Book related to the request") BookDTO book,

        @Schema(description = "Quantity of stock requested or to be decreased", example = "5") int quantity

) {
    @Override
    public String toString() {
        return "BookStockQuantityRequest(BookDTO=" + book + ", quantity=" + quantity + ")";
    }
}