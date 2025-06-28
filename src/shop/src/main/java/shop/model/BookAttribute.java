package shop.model;

import shop.dto.BookProperty;

public abstract class BookAttribute {
    private String name;
    private BookProperty type;

    public BookAttribute() {}

    public BookAttribute(String name) {
        this.name = name;
    }

    //Getters
    public String getName() {
        return name;
    }

    public BookProperty getType() {
        return type;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setType(BookProperty type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }
}
