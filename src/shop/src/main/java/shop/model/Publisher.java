package shop.model;

public class Publisher extends BookAttribute {
    private int id;

    public Publisher() {
        super();
    }

    public Publisher(int id, String name) {
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
        return "Publisher{id=" + id + ", name=" + getName() + "}";
    }
}
