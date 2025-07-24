package ca.uqam.mgl7361.lel.gp1.common.dtos.delivery;

import ca.uqam.mgl7361.lel.gp1.common.dtos.DTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;

import java.util.Date;

public class CreateDeliveryRequest extends DTO {

    private AddressDTO address;
    private Date date;
    private String inProgress;
    private OrderDTO order;

    public CreateDeliveryRequest(AddressDTO address, Date date, String status, OrderDTO order) {
        this.address = address;
        this.date = date;
        this.inProgress = status;
        this.order = order;
    }

    public CreateDeliveryRequest() {
    }

    // Getters and Setters
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
