package ca.uqam.mgl7361.lel.gp1.common.dtos.delivery;

import ca.uqam.mgl7361.lel.gp1.common.dtos.DTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;

import java.util.Date;

public class DeliveryDTO extends DTO {

    private AddressDTO address;

    private Date date;

    private String status;

    private OrderDTO order;

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
}
