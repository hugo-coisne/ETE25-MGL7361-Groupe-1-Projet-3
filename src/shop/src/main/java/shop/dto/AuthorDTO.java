package shop.dto;

public class AuthorDTO {
    private String name;

    public AuthorDTO(String name) {
        this.name = name;
    }

    // SETTERS ----------------------------------------------------------------

    public void setName(String name) {
        this.name = name;
    }

    // GETTERS ----------------------------------------------------------------

    public String getName() {
        return name;
    }
}
