package ca.uqam.mgl7361.lel.gp1.common.dtos.checkout;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.DeliveryDTO;

public record CheckoutDTO(InvoiceDTO invoice, DeliveryDTO deliveryDTO) {

}
