package ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.dto;

import ca.uqam.mgl7361.lel.gp1.order.dto.OrderDTO;

import java.sql.Date;

public class DeliveryDTO {

    private AddressDTO address;

    private Date deliveryDate;

    private String deliveryStatus;

    private OrderDTO order;

    public DeliveryDTO(AddressDTO address, Date deliveryDate, String deliveryStatus, OrderDTO order) {
        this.address = address;
        this.deliveryDate = deliveryDate;
        this.deliveryStatus = deliveryStatus;
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

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }

    public String toString() {
        return "DeliveryDTO{" +
                "address=" + address +
                ", deliveryDate=" + deliveryDate +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                '}';
    }
}
