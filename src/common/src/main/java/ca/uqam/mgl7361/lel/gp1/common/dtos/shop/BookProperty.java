package ca.uqam.mgl7361.lel.gp1.common.dtos.shop;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Properties of a book that can be searched or modified")
public enum BookProperty {

    @Schema(description = "Book title")
    TITLE("books.title", false),

    @Schema(description = "Book description")
    DESCRIPTION("books.description", false),

    @Schema(description = "ISBN number")
    ISBN("books.isbn", false),

    @Schema(description = "Date of publication")
    PUBLICATION_DATE("books.publication_date", false),

    @Schema(description = "Price of the book")
    PRICE("books.price", false),

    @Schema(description = "Number of copies in stock")
    STOCK_QUANTITY("books.stock_quantity", false),

    @Schema(description = "Name of the publisher")
    PUBLISHER("publishers.name", true),

    @Schema(description = "Category name")
    CATEGORY("categories.name", true),

    @Schema(description = "Author name")
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
