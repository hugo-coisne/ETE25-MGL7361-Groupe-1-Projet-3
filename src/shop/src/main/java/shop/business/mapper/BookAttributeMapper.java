package shop.business.mapper;

import shop.dto.BookAttributeDTO;
import shop.model.Author;
import shop.model.BookAttribute;
import shop.model.Category;
import shop.model.Publisher;

public class BookAttributeMapper {

    public static BookAttribute toModel(BookAttributeDTO dto) {
        if (dto == null) return null;
        return switch (dto.getType()) {
            case AUTHOR -> new Author(dto.getName());
            case CATEGORY -> new Category(dto.getName());
            case PUBLISHER -> new Publisher(dto.getName());
            default -> throw new IllegalArgumentException("Unsupported attribute type: " + dto.getType());
        };
    }

    public static BookAttributeDTO toDTO(BookAttribute model) {
        if (model == null) return null;
        return new BookAttributeDTO(model.getName(), model.getType());
    }
}
