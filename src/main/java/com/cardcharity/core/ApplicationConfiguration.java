package com.cardcharity.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.cardcharity")
@PropertySource("configs.properties")
public class ApplicationConfiguration {

}
