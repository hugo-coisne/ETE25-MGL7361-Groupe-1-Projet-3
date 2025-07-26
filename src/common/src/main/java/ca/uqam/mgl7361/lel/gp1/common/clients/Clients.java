package ca.uqam.mgl7361.lel.gp1.common.clients;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import io.github.cdimascio.dotenv.Dotenv;

public class Clients {

        static Dotenv dotenv = Dotenv.load();
        static String userServiceUrl = dotenv.get("USER_SERVICE_URL", "http://localhost:8081");
        static String shopServiceUrl = dotenv.get("SHOP_SERVICE_URL", "http://localhost:8082");
        static String orderServiceUrl = dotenv.get("ORDER_SERVICE_URL", "http://localhost:8083");
        static String deliveryServiceUrl = dotenv.get("DELIVERY_SERVICE_URL", "http://localhost:8084");
        static String paymentServiceUrl = dotenv.get("PAYMENT_SERVICE_URL", "http://localhost:8085");

        public static final AccountAPIClient accountClient = Feign.builder()
                        .encoder(new JacksonEncoder())
                        .decoder(new JacksonDecoder())
                        .target(AccountAPIClient.class, userServiceUrl);

        public static final CartAPIClient cartClient = Feign.builder()
                        .encoder(new JacksonEncoder())
                        .decoder(new JacksonDecoder())
                        .target(CartAPIClient.class, userServiceUrl);

        public static final BookAPIClient bookClient = Feign.builder()
                        .encoder(new JacksonEncoder())
                        .decoder(new JacksonDecoder())
                        .target(BookAPIClient.class, shopServiceUrl);

        public static final BookAttributeAPIClient bookAttributeClient = Feign.builder()
                        .encoder(new JacksonEncoder())
                        .decoder(new JacksonDecoder())
                        .target(BookAttributeAPIClient.class, shopServiceUrl);

        public static final OrderAPIClient orderClient = Feign.builder()
                        .encoder(new JacksonEncoder())
                        .decoder(new JacksonDecoder())
                        .target(OrderAPIClient.class, orderServiceUrl);

        public static final DeliveryAPIClient deliveryClient = Feign.builder()
                        .encoder(new JacksonEncoder())
                        .decoder(new JacksonDecoder())
                        .target(DeliveryAPIClient.class, deliveryServiceUrl);
        
        public static final AddressAPIClient addressClient = Feign.builder()
                        .encoder(new JacksonEncoder())
                        .decoder(new JacksonDecoder())
                        .target(AddressAPIClient.class, deliveryServiceUrl);

        public static final CheckoutAPIClient checkoutClient = Feign.builder()
                        .encoder(new JacksonEncoder())
                        .decoder(new JacksonDecoder())
                        .target(CheckoutAPIClient.class, paymentServiceUrl);

        public static final InvoiceAPIClient invoiceClient = Feign.builder()
                        .encoder(new JacksonEncoder())
                        .decoder(new JacksonDecoder())
                        .target(InvoiceAPIClient.class, paymentServiceUrl);
}
