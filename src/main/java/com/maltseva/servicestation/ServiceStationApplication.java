package com.maltseva.servicestation;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


@SpringBootApplication
public class ServiceStationApplication implements CommandLineRunner {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ServiceStationApplication(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(com.maltseva.servicestation.ServiceStationApplication.class, args);
    }

    @Override
    public void run(String... args) {

    }

}
