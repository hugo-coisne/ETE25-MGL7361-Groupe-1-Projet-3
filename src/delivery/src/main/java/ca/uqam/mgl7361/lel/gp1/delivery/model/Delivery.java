package ca.uqam.mgl7361.lel.gp1.delivery.model;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;

import java.sql.Date;

public class Delivery {

    private int id;
    private AddressDTO address;

    private Date date;

    private String status;

    private OrderDTO order;

    public Delivery(int id, AddressDTO address, Date date, String status, OrderDTO order) {
        this.id = id;
        this.address = address;
        this.date = date;
        this.status = status;
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

    public String toString() {
        return "Delivery{" +
                "address=" + address +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}