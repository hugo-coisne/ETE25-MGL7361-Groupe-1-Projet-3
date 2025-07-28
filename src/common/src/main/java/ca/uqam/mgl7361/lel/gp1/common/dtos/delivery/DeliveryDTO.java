package ca.uqam.mgl7361.lel.gp1.common.dtos.delivery;

import ca.uqam.mgl7361.lel.gp1.common.dtos.DTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "Représente une livraison")
public class DeliveryDTO extends DTO {

    @Schema(description = "Adresse de livraison", required = true)
    private AddressDTO address;

    @Schema(description = "Date prévue de la livraison", required = true, example = "2025-08-01T10:00:00Z")
    private Date date;

    @Schema(description = "Statut de la livraison", example = "en cours")
    private String status;

    @Schema(description = "Commande associée à la livraison", required = true)
    private OrderDTO order;

    @Schema(description = "Identifiant unique de la livraison", example = "123")
    private int id;

    public DeliveryDTO(AddressDTO address, Date date, String status, OrderDTO order) {
        this.address = address;
        this.date = date;
        this.status = status;
        this.order = order;
    }

    public DeliveryDTO() {
        // Default constructor for serialization/deserialization
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
