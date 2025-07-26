package ca.uqam.mgl7361.lel.gp1.delivery.presentation;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.uqam.mgl7361.lel.gp1.common.dtos.delivery.AddressDTO;
import ca.uqam.mgl7361.lel.gp1.delivery.business.AddressService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private static final Logger logger = LogManager.getLogger(AddressController.class);
    private final AddressService addressService;

    public AddressController(){
        this.addressService = AddressService.getInstance();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AddressDTO addressDTO){
        logger.info("Received request for " + addressDTO);
        try {
            addressDTO = addressService.create(addressDTO);
            return ResponseEntity.ok().body(addressDTO);
        } catch (Exception e){
            logger.error("Error during creation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") int id){
        logger.info("Received request for " + id);
        try {
            AddressDTO addressDTO = addressService.findById(id);
            return ResponseEntity.ok().body(addressDTO);
        } catch (Exception e){
            logger.error("Error during search", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("byAccountId/{id}")
    public ResponseEntity<?> findByAccountId(@PathVariable(name = "id") int id){
        logger.info("Received request for " + id);
        try {
            List<AddressDTO> addressDTOs = addressService.findByAccountId(id);
            return ResponseEntity.ok().body(addressDTOs);
        } catch (Exception e){
            logger.error("Error during search", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping
    public ResponseEntity<?> update(@RequestBody AddressDTO addressDTO){
        logger.info("Received request for " + addressDTO);
        try {
            addressService.update(addressDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Error during update", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name="id") int id){
        logger.info("Received request for " + id);
        try{
            boolean worked = addressService.delete(id);
            return ResponseEntity.ok().body(worked);
        } catch (Exception e) {
            logger.error("Error during deletion", e);
            return ResponseEntity.internalServerError().build();
        }
    }
    
}
