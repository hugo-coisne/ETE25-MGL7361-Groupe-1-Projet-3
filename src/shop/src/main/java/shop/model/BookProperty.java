package shop.model;

public enum BookProperty {
    TITLE("books.title", false),
    DESCRIPTION("books.description", false),
    ISBN("books.isbn", false),
    PUBLICATION_DATE("books.publication_date", false),
    PRICE("books.price", false),
    STOCK_QUANTITY("books.stock_quantity", false),

    PUBLISHER("publishers.name", true),
    CATEGORY("categories.name", true),
    AUTHOR("authors.name", true);

    private final String column;
    private final boolean requiresJoin;

    BookProperty(String column, boolean requiresJoin) {
        this.column = column;
        this.requiresJoin = requiresJoin;
    }

    public String getColumn() {
        return column;
    }

    public boolean isJoinRequired() {
        return requiresJoin;
    }
}
