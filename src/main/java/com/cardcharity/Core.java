package com.cardcharity;

import com.cardcharity.user.Customer;
import com.cardcharity.user.CustomerDAO;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@PropertySource({"dataBase.properties","hibernate.properties","server.properties","firebase.properties"})
@SpringBootApplication
@EnableJpaRepositories
@EnableWebSecurity
@SecurityScheme(name = "admin", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class Core {

    public static void main(String[] args) {
        SpringApplication.run(Core.class,args);
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Core.class);
        CustomerDAO userDAO = applicationContext.getBean(CustomerDAO.class);
        Customer user = userDAO.getFromFirebase("23kSwbNl5IYtBDt02aUvfDZ9tsT2");
        System.out.println(user.getEmail());
    }

    @Bean
    DataSource dataSource(Environment environment) {
        HikariConfig dbHikariConfig = new HikariConfig();
        dbHikariConfig.setJdbcUrl(environment.getProperty("dataBase.jdbcUrl"));
        dbHikariConfig.setDriverClassName(environment.getProperty("dataBase.driver"));
        dbHikariConfig.setUsername(environment.getProperty("dataBase.username"));
        dbHikariConfig.setPassword(environment.getProperty("dataBase.password"));
        return new HikariDataSource(dbHikariConfig);
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Environment environment) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("com.cardcharity");

        Properties ormProperties = new Properties();
        ormProperties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
        ormProperties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        ormProperties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        ormProperties.put("hibernate.jdbc.lob.non_contextual_creation", "true");
        entityManagerFactoryBean.setJpaProperties(ormProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    FirebaseApp firebaseApp(Environment environment) throws IOException {

        if(FirebaseApp.getApps().isEmpty()) {
            FileInputStream inputStream = new FileInputStream(environment.getProperty("firebase.authfile"));
            FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .build();
            FirebaseApp.initializeApp(firebaseOptions);
        }
        return FirebaseApp.getInstance(FirebaseApp.DEFAULT_APP_NAME);
    }
}
