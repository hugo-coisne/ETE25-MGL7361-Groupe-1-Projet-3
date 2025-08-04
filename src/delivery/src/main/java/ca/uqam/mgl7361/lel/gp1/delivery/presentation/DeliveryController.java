package ca.uqam.mgl7361.lel.gp1.delivery.presentation;

import ca.uqam.mgl7361.lel.gp1.delivery.business.DeliveryService;
import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.CreateDeliveryRequest;
import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.DeliveryDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    private static final Logger logger = LogManager.getLogger(DeliveryController.class);
    private final DeliveryService deliveryService;

    public DeliveryController() {
        this.deliveryService = new DeliveryService();
    }

    @Operation(summary = "Create a delivery", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = CreateDeliveryRequest.class))))
    @PostMapping("/create")
    public ResponseEntity<DeliveryDTO> createDelivery(
            @org.springframework.web.bind.annotation.RequestBody CreateDeliveryRequest request) {
        logger.info("Received request " + request);
        try {
            logger.debug("Creating delivery for :" + request);
            DeliveryDTO delivery = deliveryService.createDelivery(
                    request.getAddress(),
                    request.getDate(),
                    request.getInProgress(),
                    request.getOrder());
            return ResponseEntity.ok(delivery);
        } catch (Exception e) {
            logger.error("Error creating delivery", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Update delivery status to delivered", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = DeliveryDTO.class))))
    @PutMapping("/delivered")
    public ResponseEntity<Void> updateStatusToDelivered(
            @org.springframework.web.bind.annotation.RequestBody DeliveryDTO delivery) {
        logger.info("Received request " + delivery);
        try {
            logger.debug("Marking delivery as delivered: {}", delivery);
            deliveryService.updateStatusToDelivered(delivery);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error updating delivery status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get delivery statuses for a given account", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(implementation = AccountDTO.class))))
    @PostMapping("/statuses")
    public ResponseEntity<?> getOrderStatusesFor(
            @org.springframework.web.bind.annotation.RequestBody AccountDTO accountDTO) {
        logger.info("Received request for " + accountDTO);
        try {
            List<DeliveryDTO> deliveries = deliveryService.getOrderStatusesFor(accountDTO);
            logger.debug(deliveries);
            return ResponseEntity.ok().body(deliveries);
        } catch (Exception e) {
            logger.error("Error during search", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Get all orders in transit")
    @GetMapping("/in-transit")
    public ResponseEntity<List<DeliveryDTO>> getAllOrdersInTransit() {
        logger.info("Received request");
        try {
            logger.debug("Retrieving all orders in transit");
            List<DeliveryDTO> deliveries = deliveryService.getAllOrdersInTransit();
            return ResponseEntity.ok(deliveries);
        } catch (Exception e) {
            logger.error("Error fetching orders in transit", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get all delivered orders")
    @GetMapping("/delivered")
    public ResponseEntity<List<DeliveryDTO>> getAllDeliveredOrders() {
        logger.info("Received request");
        try {
            logger.debug("Retrieving all delivered orders");
            List<DeliveryDTO> deliveries = deliveryService.getAllDeliveredOrders();
            return ResponseEntity.ok(deliveries);
        } catch (Exception e) {
            logger.error("Error fetching delivered orders", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Simulate time passing", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(schema = @Schema(type = "integer", description = "in seconds", example = "3600"))))
    @PostMapping("/passTime")
    public ResponseEntity<?> pass(
            @org.springframework.web.bind.annotation.RequestBody int time) {
        logger.info("Received request for " + time);
        try {
            deliveryService.pass(time);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error while passing time", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
