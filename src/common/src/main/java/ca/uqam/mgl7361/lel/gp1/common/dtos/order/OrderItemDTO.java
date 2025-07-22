package ca.uqam.mgl7361.lel.gp1.common.dtos.order;

import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;

public class OrderItemDTO {
    private BookDTO bookDTO;
    private int quantity;

    public OrderItemDTO() {
    }

    public OrderItemDTO(BookDTO key, Integer value) {
        //TODO Auto-generated constructor stub
    }

    public BookDTO getBookDTO() {
        return this.bookDTO;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setBookDTO(BookDTO bookDTO) {
        this.bookDTO = bookDTO;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
