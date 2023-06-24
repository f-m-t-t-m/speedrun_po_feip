package ru.fefu.ecommerceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
public class ECommerceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceApiApplication.class, args);
    }

}
