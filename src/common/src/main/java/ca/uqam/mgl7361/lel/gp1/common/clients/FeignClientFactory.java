package ca.uqam.mgl7361.lel.gp1.common.clients;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import feign.Logger;

public class FeignClientFactory {

    private static final JacksonDecoder decoder = new JacksonDecoder();
    private static final JacksonEncoder encoder = new JacksonEncoder();

    public static <T> T createClient(Class<T> type, String baseUrl) {
        return Feign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .target(type, baseUrl);
    }
}