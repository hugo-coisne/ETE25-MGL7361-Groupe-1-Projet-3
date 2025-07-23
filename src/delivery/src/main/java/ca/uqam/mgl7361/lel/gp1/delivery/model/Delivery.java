package ca.uqam.mgl7361.lel.gp1.delivery.model;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;

import java.sql.Date;

public class Delivery {

    private int id;
    private AddressDTO address;

    private Date deliveryDate;

    private String deliveryStatus;

    private OrderDTO order;

    public Delivery(int id, AddressDTO address, Date deliveryDate, String deliveryStatus, OrderDTO order) {
        this.id = id;
        this.address = address;
        this.deliveryDate = deliveryDate;
        this.deliveryStatus = deliveryStatus;
        this.order = order;
    }

    public Delivery() {
        // Default constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Delivery{" +
                "address=" + address +
                ", deliveryDate=" + deliveryDate +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                '}';
    }
}