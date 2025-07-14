package ca.uqam.mgl7361.lel.gp1.lel.gp1.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.*;

import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.dto.AccountDTO;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.exception.DuplicateEmailException;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.exception.InvalidArgumentException;
import ca.uqam.mgl7361.lel.gp1.lel.gp1.account.presentation.AccountAPIImpl;

import java.io.*;
import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        AccountAPIImpl api = new AccountAPIImpl();

        server.createContext("/signup", exchange -> {
            logger.info("Received request for /signup");
            logger.info("Request method: " + exchange.getRequestMethod());
            logger.info("Request headers: " + exchange.getRequestHeaders());
            logger.info("Content type " + exchange.getRequestHeaders().getFirst("Content-Type"));

            if (!exchange.getRequestHeaders().getFirst("Content-Type").equalsIgnoreCase("application/json")) {
                logger.warn("Unsupported Content-Type : "
                        + exchange.getRequestHeaders().getFirst("Content-Type"));
                sendResponse(exchange, 415, "{\"error\":\"Unsupported Media Type\"}");
                return;
            }

            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                logger.warn("Method not allowed: " + exchange.getRequestMethod());
                sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
                return;
            }

            String body = new String(exchange.getRequestBody().readAllBytes());
            AccountDTO dto = parseAccountDTOFromJson(body);
            logger.info("Parsed AccountDTO: " + dto);
            try {
                api.signup(dto.getFirstName(), dto.getLastName(), dto.getPhone(), dto.getEmail(), dto.getPassword());
                logger.info("Signup successful with email " + dto.getEmail());
                sendResponse(exchange, 201, "{\"message\":\"Account created\"}");
            } catch (DuplicateEmailException e) {
                logger.warn("AccountDTO not created because " + e.getMessage() + " is already in database.");
                sendResponse(exchange, 409, "{\"error\":\"Email is already used\"}");
            } catch (InvalidArgumentException e) {
                logger.warn("AccountDTO not created because " + e.getProblems());
                String issues = objectMapper.writeValueAsString(e.getProblems());
                String response = String.format("{\"error\": \"Invalid argument\", \"details\":%s}", issues);
                sendResponse(exchange, 400, response);
            } catch (Exception e) {
                logger.error("Error creating account: " + e.getMessage());
                logger.error(e.getStackTrace().toString());
            }
        });

        server.createContext("/signin", exchange -> {
            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                logger.warn("Method not allowed: " + exchange.getRequestMethod());
                sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
                return;
            }
            if (!exchange.getRequestHeaders().getFirst("Content-Type").equalsIgnoreCase("application/json")) {
                logger.warn("Unsupported Content-Type : "
                        + exchange.getRequestHeaders().getFirst("Content-Type"));
                sendResponse(exchange, 415, "{\"error\":\"Unsupported media type\"}");
                return;
            }

        });

        server.setExecutor(null); // single-threaded
        server.start();
        logger.info("Server started on port 8080");
    }

    private static void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        logger.info("Sending response with status code: " + statusCode);
        byte[] bytes = response.getBytes();
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
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
