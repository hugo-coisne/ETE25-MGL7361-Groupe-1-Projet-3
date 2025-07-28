package ca.uqam.mgl7361.lel.gp1.common.dtos.checkout;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.DeliveryDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CheckoutDTO", description = "Data transfer object representing a checkout with invoice and delivery details")
public record CheckoutDTO(
        @Schema(description = "Invoice details associated with the checkout") InvoiceDTO invoice,

        @Schema(description = "Delivery details associated with the checkout") DeliveryDTO deliveryDTO) {
}
