package ca.uqam.mgl7361.lel.gp1.common.dtos.delivery;

import ca.uqam.mgl7361.lel.gp1.common.dtos.Printable;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

@Schema(description = "Représente une livraison")
public class DeliveryDTO extends Printable {

    private int id;
    private int addressId;
    private Date deliveryDate;
    private String status;
    private int orderId;

    public DeliveryDTO(int id, int addressId, Date deliveryDate, String status, int orderId) {
        this.id = id;
        this.addressId = addressId;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.orderId = orderId;
    }

    public DeliveryDTO() {
        // Default constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public Date getDate() {
        return deliveryDate;
    }

    public void setDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
