package delivery.dto;

import order.dto.OrderDTO;

import java.time.LocalDate;

public class DeliveryDTO {

    private AddressDTO address;

    private LocalDate deliveryDate;

    private String deliveryStatus;

    private OrderDTO order;

    public DeliveryDTO(AddressDTO address, LocalDate deliveryDate, String deliveryStatus, OrderDTO order) {
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
        return "DeliveryDTO{" +
                "address=" + address +
                ", deliveryDate=" + deliveryDate +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                '}';
    }
}
