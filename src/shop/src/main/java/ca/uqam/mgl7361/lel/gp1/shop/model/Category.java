package ca.uqam.mgl7361.lel.gp1.shop.model;

import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.BookProperty;

public class Category extends BookAttribute {
    private int id;

    public Category(String name) {
        super(name, BookProperty.CATEGORY);
    }

    public Category(int id, String name) {
        super(id, name, BookProperty.CATEGORY);
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
        return "Category{id=" + id + ", name=" + getName() + "}";
    }
}
