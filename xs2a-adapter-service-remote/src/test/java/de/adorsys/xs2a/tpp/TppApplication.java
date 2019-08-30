package de.adorsys.xs2a.tpp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "de.adorsys.xs2a.adapter.api.remote", defaultConfiguration = ErrorResponseDecoder.class)
@SpringBootApplication
public class TppApplication {
    public static void main(String[] args) {
        SpringApplication.run(TppApplication.class, args);
    }
}
