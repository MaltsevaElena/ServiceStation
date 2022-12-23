package com.maltseva.servicestation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServiceStationApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(com.maltseva.servicestation.ServiceStationApplication.class, args);
    }

    @Override
    public void run(String... args) {
    }
}
