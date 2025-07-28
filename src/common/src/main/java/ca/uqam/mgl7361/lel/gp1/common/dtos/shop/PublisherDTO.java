package ca.uqam.mgl7361.lel.gp1.common.dtos.shop;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data transfer object representing a book publisher")
public class PublisherDTO {

    @Schema(description = "Unique identifier of the publisher", example = "7", required = true)
    private int id;

    @Schema(description = "Name of the publisher", example = "Penguin Random House", required = true)
    private String name;

    public PublisherDTO(String name) {
        this.name = name;
    }

    public PublisherDTO() {
    }

    public PublisherDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
