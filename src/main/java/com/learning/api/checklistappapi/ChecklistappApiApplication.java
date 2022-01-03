package com.learning.api.checklistappapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

@SpringBootApplication
public class ChecklistappApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChecklistappApiApplication.class, args);
    }

    @Profile("local")
    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "PUT", "OPTIONS", "DELETE", "POST","PATCH")
                        .maxAge(900)
                        .allowedHeaders("Origin", "X-Request-With", "Content-Type", "Accept", "Authorization");
            }
        };
    }

    @Profile("aws")
    @Bean
    public WebMvcConfigurer corsAwsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "PUT", "OPTIONS", "DELETE", "POST", "PATCH")
                        .maxAge(900)
                        .allowedHeaders("Origin", "X-Request-With", "Content-Type", "Accept", "Authorization");
            }
        };
    }
    @Bean
    public OpenAPI swaggerOpenApi() {
        return new OpenAPI().info(
                new Info()
                        .title("Checklist API - Udemy")
                        .description("API desenvolvida com backend Java/Sprigboot e frontend Angular")
                        .contact(new Contact()
                                .name("Willians Augusto")
                                .email("wiliansaugusto@gmail.com")
                        )
                        .version("V1")

        );
    }
}
