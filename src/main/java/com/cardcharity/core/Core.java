package com.cardcharity.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan("com.cardcharity")
@PropertySource("configs.properties")
public class Core {
    public static ApplicationContext context;

    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(Core.class);
        SpringApplication.run(Core.class, args);
    }
}
