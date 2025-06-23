package shop.dto;

public class PublisherDTO {
    private String name;

    public PublisherDTO(String name) {
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
