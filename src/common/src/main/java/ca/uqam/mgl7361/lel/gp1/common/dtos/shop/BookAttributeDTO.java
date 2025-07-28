package ca.uqam.mgl7361.lel.gp1.common.dtos.shop;

import ca.uqam.mgl7361.lel.gp1.common.dtos.DTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data transfer object for a book attribute (author, category, publisher, etc.)")
public class BookAttributeDTO extends DTO {

    @Schema(description = "Name of the attribute", example = "J.K. Rowling", required = true)
    private String name;

    @Schema(description = "Type of the attribute", example = "AUTHOR", required = true)
    private BookProperty type;

    @Schema(description = "Unique identifier of the attribute", example = "42", required = false)
    private int id;

    public BookAttributeDTO(int id, String name, BookProperty type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public BookAttributeDTO(String name, BookProperty type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BookProperty getType() {
        return type;
    }

    public void setType(BookProperty type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
