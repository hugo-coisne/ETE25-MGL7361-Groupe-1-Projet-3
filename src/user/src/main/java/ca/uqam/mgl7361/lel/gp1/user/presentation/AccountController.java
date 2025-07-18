package ca.uqam.mgl7361.lel.gp1.user.presentation;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.uqam.mgl7361.lel.gp1.user.business.AccountService;
import ca.uqam.mgl7361.lel.gp1.user.dto.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.user.exception.DuplicateEmailException;
import ca.uqam.mgl7361.lel.gp1.user.exception.InvalidArgumentException;
import ca.uqam.mgl7361.lel.gp1.user.exception.InvalidCredentialsException;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    Logger logger = LogManager.getLogger(AccountController.class);

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AccountDTO dto) {
        logger.info("Signup request: {}", dto.getEmail());
        try {
            accountService.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Account created"));
        } catch (DuplicateEmailException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Email is already used"));
        } catch (InvalidArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Invalid argument",
                    "details", e.getProblems()));
        } catch (Exception e) {
            logger.error("Unexpected error during signup", e);
            return ResponseEntity.internalServerError().body(Map.of("error", "Internal server error"));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        try {
            accountService.signin(email, password);
            return ResponseEntity.noContent().build();
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }
    }

    @PatchMapping("/change/{property}")
    public ResponseEntity<?> changeProperty(
            @PathVariable(name = "property") String property,
            @RequestBody Map<String, String> request) {
        AccountDTO account = new AccountDTO();
        account.setEmail(request.get("email"));
        account.setPassword(request.get("password"));
        String newValue = request.get("newValue");
        logger.info("property :" + property);
        try {
            accountService.update(account, property, newValue);
            return ResponseEntity.ok(Map.of("status", "success", "message", "Property updated successfully"));
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        } catch (InvalidArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid argument", "details", e.getProblems()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Invalid property: " + property));
        } catch (DuplicateEmailException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Email is already used"));
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccount(@RequestBody AccountDTO accountDto) {
        logger.info("Deleting account for: {}", accountDto.getEmail());

        try {
            accountService.delete(accountDto);
            return ResponseEntity.ok(Map.of("status", "success", "message", "Account deleted successfully"));
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        } catch (Exception e) {
            logger.error("Unexpected error during deletion", e);
            return ResponseEntity.internalServerError().body(Map.of("error", "Internal server error"));
        }
    }
}
