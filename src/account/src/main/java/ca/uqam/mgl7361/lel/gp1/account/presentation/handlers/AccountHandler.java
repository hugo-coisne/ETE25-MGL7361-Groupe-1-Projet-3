package ca.uqam.mgl7361.lel.gp1.account.presentation.handlers;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ca.uqam.mgl7361.lel.gp1.account.dto.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.account.exception.DuplicateEmailException;
import ca.uqam.mgl7361.lel.gp1.account.exception.InvalidArgumentException;
import ca.uqam.mgl7361.lel.gp1.account.exception.InvalidCredentialsException;
import ca.uqam.mgl7361.lel.gp1.account.presentation.api.impl.AccountAPIImpl;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.common.BaseHandler;

public class AccountHandler extends BaseHandler implements HttpHandler {

    private final AccountAPIImpl api;
    static Logger logger = LogManager.getLogger(AccountHandler.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public AccountHandler(AccountAPIImpl api) {
        this.api = api;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI()
            .getPath()
            .replaceAll("/+$", "")
            .replaceFirst("/account/", "");
        String method = exchange.getRequestMethod();

        logger.info("Received request on: " + path + " [" + method + "]");


        if (path.equals("signup")){
            handleSignup(exchange);
            return;
        }

        if (path.equals("signin")){
            handleSignin(exchange);
            return;
        }

        if (path.startsWith("change")){
            logger.info("/account/change called");
            logger.info("path");
            return;
        }

        logger.warn("Not found: " + path);
        sendResponse(exchange, 404, "{\"error\":\"Not found\"}");
    }

    public void handleSignin(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            logger.warn("Method not allowed");
            sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
            return;
        }

        if (!"application/json".equalsIgnoreCase(exchange.getRequestHeaders().getFirst("Content-Type"))) {
            logger.warn("Unsupported Content-Type");
            sendResponse(exchange, 415, "{\"error\":\"Unsupported Media Type\"}");
            return;
        }

        String body = new String(exchange.getRequestBody().readAllBytes());
        Map<String, String> map = objectMapper.readValue(body, new TypeReference<>() {
        });
        String email = map.get("email");
        String password = map.get("password");

        try {
            api.signin(email, password);
            sendResponse(exchange, 204, "");
        } catch (InvalidCredentialsException e) {
            logger.warn("Invalid credentials");
            sendResponse(exchange, 401, "{\"error\":\"Invalid credentials\"}");
        }
    }

    public void handleSignup(HttpExchange exchange) throws IOException {

        if (!"application/json".equalsIgnoreCase(exchange.getRequestHeaders().getFirst("Content-Type"))) {
            logger.warn("Unsupported Content-Type");
            sendResponse(exchange, 415, "{\"error\":\"Unsupported Media Type\"}");
            return;
        }

        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            logger.warn("Method not allowed");
            sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
            return;
        }

        String body = new String(exchange.getRequestBody().readAllBytes());
        AccountDTO dto = parseAccountDTOFromJson(body);

        try {
            api.signup(dto.getFirstName(), dto.getLastName(), dto.getPhone(), dto.getEmail(), dto.getPassword());
            logger.info("Signup successful for " + dto.getEmail());
            sendResponse(exchange, 201, "{\"message\":\"Account created\"}");
        } catch (DuplicateEmailException e) {
            logger.warn("Email already exists: " + dto.getEmail());
            sendResponse(exchange, 409, "{\"error\":\"Email is already used\"}");
        } catch (InvalidArgumentException e) {
            String issues = objectMapper.writeValueAsString(e.getProblems());
            sendResponse(exchange, 400, String.format("{\"error\":\"Invalid argument\",\"details\":%s}", issues));
        } catch (Exception e) {
            logger.error("Unexpected error: ", e);
            sendResponse(exchange, 500, "{\"error\":\"Internal server error\"}");
        }
    }

    private static AccountDTO parseAccountDTOFromJson(String body) {
        logger.info("Parsing AccountDTO from JSON");
        try {
            return objectMapper.readValue(body, AccountDTO.class);
        } catch (IOException e) {
            throw new RuntimeException("Invalid JSON format: " + e.getMessage(), e);
        }
    }

}
