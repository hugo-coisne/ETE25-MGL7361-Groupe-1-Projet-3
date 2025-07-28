package ca.uqam.mgl7361.lel.gp1.order.presentation;

import ca.uqam.mgl7361.lel.gp1.order.business.OrderService;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LogManager.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        logger.info("Received request " + request);
        try {
            OrderDTO order = orderService.createOrder(request.account(), request.cart());
            logger.debug("Order successfully created: {}", order.getOrderNumber());
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            logger.error("Order creation failed for account: {}", request.account().getEmail(), e);
            return ResponseEntity.status(500).body("Order creation failed: " + e.getMessage());
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable(name = "orderId") String orderId) {
        logger.info("Received request with orderId " + orderId);
        try {
            OrderDTO order = orderService.findOrderByOrderNumber(orderId);
            logger.debug("Order retrieved successfully: {}", orderId);
            logger.debug("Found order : " + order);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            logger.error("Order not found: {}", orderId, e);
            return ResponseEntity.status(404).body("Order not found: " + e.getMessage());
        }
    }
}
