package ca.uqam.mgl7361.lel.gp1.common.dtos.order;

import ca.uqam.mgl7361.lel.gp1.common.dtos.shop.CartDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import io.swagger.v3.oas.annotations.media.Schema;

public record OrderRequest(
        @Schema(description = "User account information", required = true) AccountDTO account,

        @Schema(description = "Shopping cart details", required = true) CartDTO cart) {
}
