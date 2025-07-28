package ca.uqam.mgl7361.lel.gp1.common.dtos.shop;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Schema(description = "Request object to set or remove properties for a book")
public record BookPropertiesRequest(

        @Schema(description = "Book to update") BookDTO book,

        @Schema(description = "Properties to set or remove with associated values") Map<BookProperty, List<String>> properties

) {
}
