package ca.uqam.mgl7361.lel.gp1.common.dtos.shop;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data transfer object representing an author")
public class AuthorDTO {

    @Schema(description = "Unique identifier of the author", example = "12", required = true)
    private int id;

    @Schema(description = "Full name of the author", example = "George Orwell", required = true)
    private String name;

    public AuthorDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public AuthorDTO() {
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
    public String toString(){
        return this.name;
    }
}
