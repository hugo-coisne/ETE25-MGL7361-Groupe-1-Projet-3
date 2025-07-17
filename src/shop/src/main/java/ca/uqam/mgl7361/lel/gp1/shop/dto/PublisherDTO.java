package ca.uqam.mgl7361.lel.gp1.shop.dto;

public class PublisherDTO {
    private int id;
    private String name;

    public PublisherDTO(String name) {
        this.name = name;
    }

    public PublisherDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public PublisherDTO() {} // Default constructor for serialization/deserialization

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
}
