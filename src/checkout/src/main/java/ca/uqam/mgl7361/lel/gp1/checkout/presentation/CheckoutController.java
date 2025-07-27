package ca.uqam.mgl7361.lel.gp1.checkout.presentation;

import ca.uqam.mgl7361.lel.gp1.checkout.business.CheckoutService;
import ca.uqam.mgl7361.lel.gp1.common.dtos.checkout.CheckoutDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.checkout.CheckoutRequest;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
@Tag(name = "Invoice", description = "Endpoints for invoice operations")
public class CheckoutController {

    private static final Logger logger = LoggerFactory.getLogger(CheckoutController.class);
    private final CheckoutService invoiceService;

    public CheckoutController() {
        this.invoiceService = new CheckoutService();
    }

    @PostMapping
    public ResponseEntity<?> checkout(@RequestBody CheckoutRequest request) {
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
