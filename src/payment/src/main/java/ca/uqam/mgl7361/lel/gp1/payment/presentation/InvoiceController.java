package ca.uqam.mgl7361.lel.gp1.payment.presentation;

import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.payment.business.InvoiceService;
import ca.uqam.mgl7361.lel.gp1.payment.dto.InvoiceDTO;
import ca.uqam.mgl7361.lel.gp1.payment.dto.PaymentMethod;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoices")
@Tag(name = "Invoice", description = "Endpoints for invoice operations")
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    private final InvoiceService invoiceService;

    public InvoiceController() {
        this.invoiceService = new InvoiceService();
    }

    @Operation(summary = "Create a new invoice", description = "Generates an invoice for a given account and payment method", requestBody = @RequestBody(description = "Account and payment method details", required = true, content = @Content(schema = @Schema(implementation = InvoiceRequest.class))), responses = {
            @ApiResponse(responseCode = "200", description = "Invoice created successfully", content = @Content(schema = @Schema(implementation = InvoiceDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PostMapping
    public ResponseEntity<InvoiceDTO> createInvoice(@RequestBody InvoiceRequest request) {
        try {
            logger.info("Received request to create invoice for : {}", request);
            InvoiceDTO invoice = invoiceService.createInvoice(request.account, request.paymentMethod);
            logger.info("Invoice created : {}", invoice.toString());
            return ResponseEntity.ok(invoice);
        } catch (Exception e) {
            logger.error("Error while creating invoice", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // DTO to encapsulate request body
    record InvoiceRequest(AccountDTO account, PaymentMethod paymentMethod) {
    }
}
