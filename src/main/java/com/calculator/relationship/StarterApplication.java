package com.calculator.relationship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;

@ComponentScan("com.calculator.relationship")
@SpringBootApplication
public class StarterApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder application) {
        return application.sources(StarterApplication.class);
    }

    @RequestMapping("/appState")
    String appState() {
        return "OK";
    }

    public static void main(String[] args) {
        SpringApplication.run(StarterApplication.class, args);
    }

}
