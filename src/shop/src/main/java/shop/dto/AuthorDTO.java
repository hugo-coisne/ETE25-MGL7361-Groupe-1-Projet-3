package shop.dto;

public class AuthorDTO {
    private final int id;
    private final String name;

    public AuthorDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
