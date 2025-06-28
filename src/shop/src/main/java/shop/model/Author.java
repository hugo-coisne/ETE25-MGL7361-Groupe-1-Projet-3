package shop.model;

public class Author extends BookAttribute {
    private int id;

    public Author() {
        super();
    }

    public Author(int id, String name) {
        super(name);
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
