package ca.uqam.mgl7361.lel.gp1.checkout.presentation;

import ca.uqam.mgl7361.lel.gp1.checkout.business.CheckoutService;
import ca.uqam.mgl7361.lel.gp1.common.dtos.checkout.CheckoutDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.checkout.CheckoutRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/checkout")
@Tag(name = "Checkout", description = "Endpoints for checkout operations")
public class CheckoutController {

    private static final Logger logger = LoggerFactory.getLogger(CheckoutController.class);
    private final CheckoutService invoiceService;

    public CheckoutController() {
        this.invoiceService = new CheckoutService();
    }

    @Operation(summary = "Process a checkout", description = "Creates an invoice for the given checkout request")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Checkout completed successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> checkout(
            @RequestBody @Parameter(description = "Checkout request with account, payment method and address", required = true) CheckoutRequest request) {

        logger.info("Received request for : {}", request);
        try {
            CheckoutDTO checkoutDTO = invoiceService.checkout(request.accountDto(), request.paymentmethod(),
                    request.address());
            logger.debug("Invoice created : {}", checkoutDTO.invoice().toString());
            return ResponseEntity.ok(checkoutDTO);
        } catch (Exception e) {
            logger.error("Error while creating invoice", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
