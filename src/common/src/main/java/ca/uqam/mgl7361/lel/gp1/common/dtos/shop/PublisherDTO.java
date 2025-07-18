package ca.uqam.mgl7361.lel.gp1.common.dtos.shop;

public class PublisherDTO {
    private int id;
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
}
