package com.cardcharity.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
@SpringBootApplication
public class Core {
    public static ApplicationContext context;

    public static void main(String[] args) {
        //context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        SpringApplication.run(Core.class, args);
    }
}
