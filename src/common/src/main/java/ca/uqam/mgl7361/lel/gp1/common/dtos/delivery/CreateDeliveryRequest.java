package ca.uqam.mgl7361.lel.gp1.common.dtos.delivery;

import ca.uqam.mgl7361.lel.gp1.common.dtos.Printable;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "Requête pour créer une livraison")
public class CreateDeliveryRequest extends Printable {

    @Schema(description = "Adresse de livraison", required = true)
    private AddressDTO address;

    @Schema(description = "Date prévue de livraison", required = true, example = "2025-08-01T10:00:00Z")
    private Date date;

    @Schema(description = "Statut de la livraison (en cours ou non)", example = "true")
    private String inProgress;

    @Schema(description = "Commande associée à la livraison", required = true)
    private OrderDTO order;

    public CreateDeliveryRequest(AddressDTO address, Date date, String status, OrderDTO order) {
        this.address = address;
        this.date = date;
        this.inProgress = status;
        this.order = order;
    }

    public CreateDeliveryRequest() {
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getInProgress() {
        return inProgress;
    }

    public void setInProgress(String inProgress) {
        this.inProgress = inProgress;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }
}
