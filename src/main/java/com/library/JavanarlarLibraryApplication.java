package com.library;

import com.library.controller.ImageController;
import com.library.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;


@SpringBootApplication
public class JavanarlarLibraryApplication {


    public static void main(String[] args) {
        new File(ImageController.uploadDirectory).mkdir();
        SpringApplication.run(JavanarlarLibraryApplication.class, args);

    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*").allowedMethods("*");
                //  WebMvcConfigurer.super.addCorsMappings(registry);
            }
        };
    }



}
