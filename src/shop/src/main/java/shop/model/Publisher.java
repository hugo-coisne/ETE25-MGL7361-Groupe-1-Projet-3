package shop.model;

import shop.dto.BookProperty;

public class Publisher extends BookAttribute {
    private int id;

    public Publisher(String name) {
        super(name, BookProperty.PUBLISHER);
    }

    public Publisher(int id, String name) {
        super(id, name, BookProperty.PUBLISHER);
        this.id = id;
    }

    // Getter
    public int getId() {
        return id;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Publisher{id=" + id + ", name=" + getName() + "}";
    }
}
