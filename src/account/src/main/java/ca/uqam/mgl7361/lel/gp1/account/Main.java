package ca.uqam.mgl7361.lel.gp1.account;

import com.sun.net.httpserver.*;

import ca.uqam.mgl7361.lel.gp1.account.presentation.api.impl.AccountAPIImpl;
import ca.uqam.mgl7361.lel.gp1.account.presentation.handlers.AccountHandler;

import java.io.*;
import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        AccountAPIImpl api = new AccountAPIImpl();

        server.createContext("/account", new AccountHandler(api));

        server.setExecutor(null); // single-threaded
        server.start();
        logger.info("Server started on port 8080");
    }
}
