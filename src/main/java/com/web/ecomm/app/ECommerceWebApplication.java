package com.web.ecomm.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@Slf4j
@SpringBootApplication
@EnableCaching
public class ECommerceWebApplication {

    public static void main(String[] args) {

        Long start = System.currentTimeMillis();
        SpringApplication.run(ECommerceWebApplication.class, args);

        Long end = System.currentTimeMillis();
        log.info("Application Started Successfully in {} milliseconds", end - start);
    }
}
