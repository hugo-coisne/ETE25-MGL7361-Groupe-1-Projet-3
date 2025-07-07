package shop.dto;

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
