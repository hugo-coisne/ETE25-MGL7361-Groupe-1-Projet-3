package ca.uqam.mgl7361.lel.gp1.payment.presentation;

import ca.uqam.mgl7361.lel.gp1.payment.business.InvoiceService;
import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.CheckoutDTO;
import ca.uqam.mgl7361.lel.gp1.common.dtos.payment.CheckoutRequest;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
@Tag(name = "Invoice", description = "Endpoints for invoice operations")
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    private final InvoiceService invoiceService;

    public InvoiceController() {
        this.invoiceService = new InvoiceService();
    }

    @PostMapping
    public ResponseEntity<?> checkout(@RequestBody CheckoutRequest request) {
        logger.info("Received request for : {}", request);
        try {
            CheckoutDTO checkoutDTO = invoiceService.checkout(request.accountDto(), request.paymentMethod(), request.address());
            logger.debug("Invoice created : {}", checkoutDTO.invoice().toString());
            return ResponseEntity.ok(checkoutDTO);
        } catch (Exception e) {
            logger.error("Error while creating invoice", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
