package ca.uqam.mgl7361.lel.gp1.user.presentation;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.uqam.mgl7361.lel.gp1.user.business.AccountService;
import ca.uqam.mgl7361.lel.gp1.common.dtos.user.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.user.exception.DuplicateEmailException;
import ca.uqam.mgl7361.lel.gp1.user.exception.InvalidArgumentException;
import ca.uqam.mgl7361.lel.gp1.user.exception.InvalidCredentialsException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/account")
@Tag(name = "Account", description = "Endpoints for account management")
public class AccountController {
    private final AccountService accountService;

    Logger logger = LogManager.getLogger(AccountController.class);

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Sign up a new account")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created"),
            @ApiResponse(responseCode = "409", description = "Email already used"),
            @ApiResponse(responseCode = "400", description = "Invalid argument"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestBody(description = "Account details to create", required = true, content = @Content(schema = @Schema(implementation = AccountDTO.class))) @org.springframework.web.bind.annotation.RequestBody AccountDTO dto) {
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

    @Operation(summary = "Sign in with email and password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Signed in successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/signin")
    public ResponseEntity<?> signin(
            @RequestBody(description = "Credentials map containing email and password", required = true, content = @Content(schema = @Schema(implementation = Map.class))) @org.springframework.web.bind.annotation.RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        logger.info("Signin request for " + email);
        String password = credentials.get("password");
        try {
            AccountDTO account = accountService.signin(email, password);
            return ResponseEntity.status(200).body(account);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }
    }

    @Operation(summary = "Change a property of an account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Property updated successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "400", description = "Invalid argument"),
            @ApiResponse(responseCode = "404", description = "Invalid property"),
            @ApiResponse(responseCode = "409", description = "Email already used")
    })
    @PatchMapping("/change/{property}")
    public ResponseEntity<?> changeProperty(
            @PathVariable(name = "property", required = true) String property,
            @RequestBody(description = "Request containing email, password, and new value", required = true, content = @Content(schema = @Schema(implementation = Map.class))) @org.springframework.web.bind.annotation.RequestBody Map<String, String> request) {
        logger.info("Received request " + request);
        AccountDTO account = new AccountDTO();
        account.setEmail(request.get("email"));
        account.setPassword(request.get("password"));
        String newValue = request.get("newValue");
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

    @Operation(summary = "Delete an account")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping
    public ResponseEntity<?> deleteAccount(
            @RequestBody(description = "Account details to delete", required = true, content = @Content(schema = @Schema(implementation = AccountDTO.class))) @org.springframework.web.bind.annotation.RequestBody AccountDTO accountDto) {
        logger.info("Received request for: {}", accountDto.getEmail());

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
