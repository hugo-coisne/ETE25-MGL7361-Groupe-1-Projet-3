package ca.uqam.mgl7361.lel.gp1.common.clients;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.github.cdimascio.dotenv.Dotenv;

public class Clients {

    static Dotenv dotenv = Dotenv.load();
    static String userServiceUrl = dotenv.get("USER_SERVICE_URL", "http://localhost:8081");
    // static String orderServiceUrl = dotenv.get("ORDER_SERVICE_URL", "http://localhost:8082");


    public static final AccountAPIClient userClient = Feign.builder()
            .encoder(new JacksonEncoder())
            .decoder(new JacksonDecoder())
            .target(AccountAPIClient.class, userServiceUrl);

    // public static final OrderServiceClient orderClient = Feign.builder()
    //         .encoder(new JacksonEncoder())
    //         .decoder(new JacksonDecoder())
    //         .target(OrderServiceClient.class, orderServiceUrl);
}
