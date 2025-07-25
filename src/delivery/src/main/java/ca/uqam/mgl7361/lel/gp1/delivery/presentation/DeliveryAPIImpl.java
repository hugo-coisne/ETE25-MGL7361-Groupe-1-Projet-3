package ca.uqam.mgl7361.lel.gp1.delivery.presentation;

import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.business.DeliveryService;
import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.DeliveryDTO;

import java.util.Date;
import java.util.List;

public class DeliveryAPIImpl implements DeliveryAPI {

    private final DeliveryService deliveryService;

    public DeliveryAPIImpl() {
        this.deliveryService = new DeliveryService();
    }

    @Override
    public DeliveryDTO createDelivery(AddressDTO address, Date date, String inProgress, OrderDTO order) {
        try {
            return deliveryService.createDelivery(address, date, inProgress, order);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // or handle the exception as needed
        }
    }

    @Override
    public void updateStatusToDelivered(DeliveryDTO delivery) {
        try {
            deliveryService.updateStatusToDelivered(delivery);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public List<DeliveryDTO> getAllOrdersInTransit() {
        try {
            return deliveryService.getAllOrdersInTransit();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null; // or handle the exception as needed
        }
    }

    // public List<DeliveryDTO> getAllOrdersInTransit(AccountDTO account) throws
    // Exception {
    // return deliveryService.getAllOrdersInTransit(account);
    // }

    public List<DeliveryDTO> getAllDeliveredOrders() {
        try {
            return deliveryService.getAllDeliveredOrders();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null; // or handle the exception as needed
        }
    }

}
