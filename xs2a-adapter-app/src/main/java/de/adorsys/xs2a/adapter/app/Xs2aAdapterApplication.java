package de.adorsys.xs2a.adapter.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "de.adorsys.xs2a.adapter")
public class Xs2aAdapterApplication {
    public static void main(String[] args) {
        SpringApplication.run(Xs2aAdapterApplication.class, args);
    }
}
