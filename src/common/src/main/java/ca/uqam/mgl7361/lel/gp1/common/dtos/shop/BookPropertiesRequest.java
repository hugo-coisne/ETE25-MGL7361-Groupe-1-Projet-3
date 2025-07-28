package ca.uqam.mgl7361.lel.gp1.common.dtos.shop;

import java.util.List;
import java.util.Map;

public record BookPropertiesRequest(
                BookDTO book,
                Map<BookProperty, List<String>> properties) {
}
