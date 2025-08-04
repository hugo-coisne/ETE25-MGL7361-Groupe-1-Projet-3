package ca.uqam.mgl7361.lel.gp1.order.presentation;

import ca.uqam.mgl7361.lel.gp1.order.business.OrderService;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.order.OrderRequest;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@Tag(name = "Orders", description = "Endpoints for order management")
public class OrderController {

    private static final Logger logger = LogManager.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Create a new order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "500", description = "Order creation failed")
    })
    @PostMapping
    public ResponseEntity<?> createOrder(
            @RequestBody(description = "Order request containing account and cart", required = true, content = @Content(schema = @Schema(implementation = OrderRequest.class))) @org.springframework.web.bind.annotation.RequestBody OrderRequest request) {
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

    @Operation(summary = "Get order details by order number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<?> getOrderByNumber(
            @Parameter(description = "Order ID", required = true) @PathVariable(name = "orderNumber") String orderNumber) {
        logger.info("Received request with orderId " + orderNumber);
        try {
            OrderDTO order = orderService.findOrderByOrderNumber(orderNumber);
            logger.debug("Order retrieved successfully: {}", orderNumber);
            logger.debug("Found order : " + order);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            logger.error("Order not found: {}", orderNumber, e);
            return ResponseEntity.status(404).body("Order not found: " + e.getMessage());
        }
    }

    @Operation(summary = "Get order details by order ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("/id/{orderId}")
    public ResponseEntity<?> getOrderById(
            @Parameter(description = "Order ID", required = true) @PathVariable(name = "orderId") int orderId) {
        logger.info("getOrderById(" + orderId + ")");
        try {
            OrderDTO order = orderService.getOrderById(orderId);
            logger.debug("Order retrieved successfully: {}", orderId);
            logger.debug("Found order : " + order);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            logger.error("Order not found: {}", orderId, e);
            return ResponseEntity.status(404).body("Order not found: " + e.getMessage());
        }
    }

    @PostMapping("/account")
    public ResponseEntity<?> getOrdersFor(@org.springframework.web.bind.annotation.RequestBody AccountDTO accountDTO) {
        try {
            logger.info("getOrdersFor("+accountDTO+")");
            List<OrderDTO> orders = orderService.getOrdersFor(accountDTO);
            return ResponseEntity.ok().body(orders);
        } catch (Exception e) {
            logger.error(e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
