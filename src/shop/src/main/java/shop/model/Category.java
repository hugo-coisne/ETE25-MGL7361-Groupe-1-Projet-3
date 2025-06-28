package shop.model;

public class Category extends BookAttribute {
    private int id;

    public Category() {
        super();
    }

    public Category(int id, String name) {
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

    @Override
    public String toString() {
        return "Category{id=" + id + ", name=" + getName() + "}";
    }
}
