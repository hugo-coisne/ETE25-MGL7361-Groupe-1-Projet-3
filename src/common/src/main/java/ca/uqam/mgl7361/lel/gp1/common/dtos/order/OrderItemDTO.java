package ca.uqam.mgl7361.lel.gp1.common.dtos.order;

import ca.uqam.mgl7361.lel.gp1.common.dtos.DTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents an item in an order, including the book and quantity.")
public class OrderItemDTO extends DTO {

    @Schema(description = "The book associated with this order item", required = true)
    private BookDTO bookDTO;

    @Schema(description = "The quantity of the book ordered", example = "2", required = true)
    private int quantity;

    public OrderItemDTO() {
    }

    public OrderItemDTO(BookDTO key, Integer value) {
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
