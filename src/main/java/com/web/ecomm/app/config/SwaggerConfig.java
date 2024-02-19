package com.web.ecomm.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
@ComponentScan
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        String version = Optional.ofNullable(System.getProperty("api.version")).orElse("1.0.0");
        return new OpenAPI().info(new Info()
                .title("E Commerce Application")
                .description("E Commerce Application For Online Shopping")
                .version(version)
                .termsOfService("http://swagger.io/terms/")
                .license(new License().url("http://unlicense.org"))
                .contact(new Contact())
        );
    }

}
