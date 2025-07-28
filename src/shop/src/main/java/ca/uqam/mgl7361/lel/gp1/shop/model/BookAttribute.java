package ca.uqam.mgl7361.lel.gp1.shop.model;

import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookProperty;

public abstract class BookAttribute {
    private String name;
    private BookProperty type;

    public BookAttribute() {}

    public BookAttribute(String name) {
        this.name = name;
    }

    public BookAttribute(String name, BookProperty type) {
        this.name = name;
        this.type = type;
    }

    public BookAttribute(int id, String name, BookProperty type) {
        this.name = name;
        this.type = type;
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
