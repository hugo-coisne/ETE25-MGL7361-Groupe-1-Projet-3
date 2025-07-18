package ca.uqam.mgl7361.lel.gp1.order.presentation;

import ca.uqam.mgl7361.lel.gp1.common.dtos.account.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.account.CartDTO;
import ca.uqam.mgl7361.lel.gp1.order.business.OrderService;
import ca.uqam.mgl7361.lel.gp1.order.dto.OrderDTO;

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

    @Operation(summary = "Create a new order from account and cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order successfully created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))),
        @ApiResponse(responseCode = "500", description = "Order creation failed",
            content = @Content(mediaType = "application/json"))
    })
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        logger.info("Received request to create order for account: {}", request.account().getEmail());
        try {
            OrderDTO order = orderService.createOrder(request.account(), request.cart());
            logger.info("Order successfully created: {}", order.getOrderNumber());
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            logger.error("Order creation failed for account: {}", request.account().getEmail(), e);
            return ResponseEntity.status(500).body("Order creation failed: " + e.getMessage());
        }
    }

    @Operation(summary = "Retrieve an order by its order number")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Order successfully retrieved",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))),
        @ApiResponse(responseCode = "404", description = "Order not found",
            content = @Content)
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable String orderId) {
        logger.info("Received request to retrieve order with ID: {}", orderId);
        try {
            OrderDTO order = orderService.findOrderByOrderNumber(orderId);
            logger.info("Order retrieved successfully: {}", orderId);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            logger.error("Order not found: {}", orderId, e);
            return ResponseEntity.status(404).body("Order not found: " + e.getMessage());
        }
    }

    /**
     * DTO used to wrap order creation payload.
     */
    @Schema(name = "OrderRequest", description = "Payload to create an order")
    public record OrderRequest(
        @Schema(description = "Account information", required = true)
        AccountDTO account,

        @Schema(description = "Cart information", required = true)
        CartDTO cart
    ) {}
}
