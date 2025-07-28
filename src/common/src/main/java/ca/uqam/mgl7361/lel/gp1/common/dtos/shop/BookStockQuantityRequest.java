package ca.uqam.mgl7361.lel.gp1.common.dtos.shop;

public record BookStockQuantityRequest(BookDTO book, int quantity) {

    @Override
    public String toString() {
        return "BookStockQuantityRequest(BookDTO=" + book + ", quantity=" + quantity + ")";
    }
}
