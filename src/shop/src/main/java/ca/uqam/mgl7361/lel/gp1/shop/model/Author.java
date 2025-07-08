package ca.uqam.mgl7361.lel.gp1.shop.model;

import ca.uqam.mgl7361.lel.gp1.shop.dto.BookProperty;

public class Author extends BookAttribute {
    private int id;

    public Author(String name) {
        super(name, BookProperty.AUTHOR);
    }

    public Author(int id, String name) {
        super(id, name, BookProperty.AUTHOR);
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

    // toString (hérite deja de BookAttribute mais je l'ai étendu)
    @Override
    public String toString() {
        return "Author{id=" + id + ", name=" + getName() + "}";
    }
}
