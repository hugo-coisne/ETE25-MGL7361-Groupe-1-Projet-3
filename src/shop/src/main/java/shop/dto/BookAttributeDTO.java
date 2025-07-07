package shop.dto;

public class BookAttributeDTO {
    private String name;
    private BookProperty type;
    private int id;

    public BookAttributeDTO(int id, String name, BookProperty type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public BookAttributeDTO(String name, BookProperty type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BookProperty getType() {
        return type;
    }

    public void setType(BookProperty type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
