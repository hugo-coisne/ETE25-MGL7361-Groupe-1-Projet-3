package ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.business;

import ca.uqam.mgl7361.lel.gp1.account.dto.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.dto.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.dto.DeliveryDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.delivery.persistence.DeliveryDAO;
import ca.uqam.mgl7361.lel.gp1.order.dto.OrderDTO;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeliveryServiceTest {

    private DeliveryService service;
    private DeliveryDAO deliveryDAO;

    @BeforeAll
    public void setup() throws SQLException {
        deliveryDAO = new DeliveryDAO(); // DAO r�el connect� � la base de test
        service = new DeliveryService(deliveryDAO);
    }

    @Test
    public void testCreateDelivery() throws Exception {
        AddressDTO address = new AddressDTO();
        Date date = Date.valueOf(LocalDate.now());
        String status = "In Progress";
        OrderDTO order = new OrderDTO(
                "ORDER-002",
                Date.valueOf(LocalDate.now()),
                20.01f,
                null
        );

        DeliveryDTO delivery = service.createDelivery(address, date, status, order);

        assertNotNull(delivery);
        assertEquals(status, delivery.getDeliveryStatus());
        assertEquals(address, delivery.getAddress());
        assertEquals(date, delivery.getDeliveryDate());
        assertEquals(order, delivery.getOrder());
    }

    @Test
    public void testUpdateDeliveryStatus() throws Exception {
        DeliveryDTO delivery = service.createDelivery(new AddressDTO(), Date.valueOf(LocalDate.now()), "In Transit", new OrderDTO());
        service.updateDeliveryStatus(delivery, "Delivered");
        assertEquals("Delivered", delivery.getDeliveryStatus());
    }

    @Test
    public void testGetAllOrdersInTransit() throws SQLException {
        List<DeliveryDTO> deliveries = service.getAllOrdersInTransit();
        assertNotNull(deliveries);
        // Optionnel : tu peux v�rifier la taille ou le contenu si tu as des donn�es de test
    }

    @Test
    public void testGetAllOrdersDelivered() throws SQLException {
        List<DeliveryDTO> deliveries = service.getAllOrdersDelivered();
        assertNotNull(deliveries);
    }

    @Test
    public void testGetAllOrdersNotDelivered() throws SQLException {
        List<DeliveryDTO> deliveries = service.getAllOrdersNotDelivered();
        assertNotNull(deliveries);
    }

    @Test
    public void testGetAllOrdersInTransitWithAccount() throws SQLException {
        AccountDTO account = new AccountDTO();
        account.setId(1);  // Assure-toi que cet id existe en base test
        List<DeliveryDTO> deliveries = service.getAllOrdersInTransit(account);
        assertNotNull(deliveries);
    }
}
