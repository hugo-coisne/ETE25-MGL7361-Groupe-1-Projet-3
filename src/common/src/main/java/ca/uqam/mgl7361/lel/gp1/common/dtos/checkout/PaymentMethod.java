package ca.uqam.mgl7361.lel.gp1.common.dtos.checkout;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Méthode de paiement possible pour une commande")
public enum PaymentMethod {
    @Schema(description = "Paiement par carte bancaire")
    CARD,

    @Schema(description = "Paiement via PayPal")
    PAYPAL,
}
