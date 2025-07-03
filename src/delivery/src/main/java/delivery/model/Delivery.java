package delivery.model;

import delivery.dto.AddressDTO;
import order.dto.OrderDTO;

import java.time.LocalDate;

public class Delivery {

    private int id;
    private AddressDTO address;

    private LocalDate deliveryDate;

    private String deliveryStatus;

    private OrderDTO order;

    public Delivery(int id, AddressDTO address, LocalDate deliveryDate, String deliveryStatus, OrderDTO order) {
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

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
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