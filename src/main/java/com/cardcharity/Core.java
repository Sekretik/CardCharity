package com.cardcharity;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@PropertySource({"dataBase.properties","hibernate.properties","server.properties"})
@SpringBootApplication
@EnableJpaRepositories
public class Core {
    public static void main(String[] args) {
        SpringApplication.run(Core.class,args);
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
        entityManagerFactoryBean.setJpaProperties(ormProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
