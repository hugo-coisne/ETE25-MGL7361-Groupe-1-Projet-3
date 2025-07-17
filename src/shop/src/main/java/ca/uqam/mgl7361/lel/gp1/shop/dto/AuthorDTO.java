package ca.uqam.mgl7361.lel.gp1.shop.dto;

public class AuthorDTO {
    private int id;
    private String name;

    public AuthorDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public AuthorDTO() {
        // Default constructor for serialization/deserialization
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
}
