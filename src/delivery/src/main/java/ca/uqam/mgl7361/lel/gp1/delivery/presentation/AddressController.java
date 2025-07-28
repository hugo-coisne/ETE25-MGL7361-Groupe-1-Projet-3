package ca.uqam.mgl7361.lel.gp1.delivery.presentation;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.business.AddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/addresses")
@Tag(name = "Addresses", description = "Endpoints for managing addresses")
public class AddressController {

    private static final Logger logger = LogManager.getLogger(AddressController.class);
    private final AddressService addressService;

    public AddressController() {
        this.addressService = AddressService.getInstance();
    }

    @Operation(summary = "Create a new address", description = "Creates a new address and returns the created address")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address created successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody @Parameter(description = "Address object to create", required = true) AddressDTO addressDTO) {
        logger.info("Received request for " + addressDTO);
        try {
            addressDTO = addressService.create(addressDTO);
            return ResponseEntity.ok().body(addressDTO);
        } catch (Exception e) {
            logger.error("Error during creation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Find address by ID", description = "Retrieve an address by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/byId/{id}")
    public ResponseEntity<?> findById(
            @Parameter(description = "ID of the address to retrieve", required = true) @PathVariable(name = "id") int id) {
        logger.info("Received request for " + id);
        try {
            AddressDTO addressDTO = addressService.findById(id);
            return ResponseEntity.ok().body(addressDTO);
        } catch (Exception e) {
            logger.error("Error during search", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Find addresses by account ID", description = "Retrieve all addresses associated with a specific account ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Addresses retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("byAccountId/{id}")
    public ResponseEntity<?> findByAccountId(
            @Parameter(description = "Account ID for which to retrieve addresses", required = true) @PathVariable(name = "id") int id) {
        logger.info("Received request for " + id);
        try {
            List<AddressDTO> addressDTOs = addressService.findByAccountId(id);
            return ResponseEntity.ok().body(addressDTOs);
        } catch (Exception e) {
            logger.error("Error during search", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Update an address", description = "Update an existing address")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address updated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping
    public ResponseEntity<?> update(
            @RequestBody @Parameter(description = "Address object with updated fields", required = true) AddressDTO addressDTO) {
        logger.info("Received request for " + addressDTO);
        try {
            addressService.update(addressDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error during update", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Delete an address by ID", description = "Deletes the address identified by the given ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Address deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @Parameter(description = "ID of the address to delete", required = true) @PathVariable(name = "id") int id) {
        logger.info("Received request for " + id);
        try {
            boolean worked = addressService.delete(id);
            return ResponseEntity.ok().body(worked);
        } catch (Exception e) {
            logger.error("Error during deletion", e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
