package com.web.ecomm.app.config;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> enableDefaultServlet() {
        return (factory) -> factory.setRegisterDefaultServlet(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiRequestInterceptor()).addPathPatterns(
                "/address/**",
                "/user/**",
                "/orders/**",
                "/products/**"
        );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/address/**").allowedOrigins("https://localhost:8080", "https://127.0.0.1:8080").allowedMethods("*");
        registry.addMapping("/user/**").allowedOrigins("https://localhost:8080", "https://127.0.0.1:8080").allowedMethods("*");
        registry.addMapping("/orders/**").allowedOrigins("https://localhost:8080", "https://127.0.0.1:8080").allowedMethods("*");
        registry.addMapping("/products/**").allowedOrigins("https://localhost:8080", "https://127.0.0.1:8080").allowedMethods("*");
    }

    @Bean
    public APIRequestInterceptor apiRequestInterceptor() {
        return new APIRequestInterceptor();
    }
}
