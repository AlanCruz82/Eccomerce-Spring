package com.spring.eccomerce;

import com.spring.eccomerce.exception.PedidoNotFoundException;
import com.spring.eccomerce.service.PedidoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EccomerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EccomerceApplication.class, args);
    }
}
