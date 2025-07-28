package ca.uqam.mgl7361.lel.gp1.common.dtos.shop;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data transfer object representing a book category")
public class CategoryDTO {

    @Schema(description = "Unique identifier of the category", example = "5", required = true)
    private int id;

    @Schema(description = "Name of the category", example = "Science Fiction", required = true)
    private String name;

    public CategoryDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
