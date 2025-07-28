package ca.uqam.mgl7361.lel.gp1.common.dtos.shop;

import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to add or remove a book to/from a user's cart")
public record CartBookRequest(

        @Schema(description = "User performing the action") AccountDTO account,

        @Schema(description = "Book to add or remove") BookDTO book

) {
}
