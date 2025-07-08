package ca.uqam.mgl7361.lel.gp1.shop.business.mapper;

import ca.uqam.mgl7361.lel.gp1.shop.dto.BookAttributeDTO;
import ca.uqam.mgl7361.lel.gp1.shop.model.Author;
import ca.uqam.mgl7361.lel.gp1.shop.model.BookAttribute;
import ca.uqam.mgl7361.lel.gp1.shop.model.Category;
import ca.uqam.mgl7361.lel.gp1.shop.model.Publisher;

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
