package com.cardcharity;

import com.cardcharity.card.Card;
import com.cardcharity.card.CardRepository;
import com.cardcharity.owner.Owner;
import com.cardcharity.owner.OwnerRepository;
import com.cardcharity.shop.Shop;
import com.cardcharity.shop.ShopRepository;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

@PropertySource({"dataBase.properties","hibernate.properties","server.properties"})
@SpringBootApplication
@EnableJpaRepositories
@EnableWebSecurity
@SecurityScheme(name = "admin", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class Core {
    public static void main(String[] args) {
        //SpringApplication.run(Core.class,args);
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Core.class);
        ShopRepository shopRepository = applicationContext.getBean(ShopRepository.class);
        CardRepository cardRepository = applicationContext.getBean(CardRepository.class);
        OwnerRepository ownerRepository = applicationContext.getBean(OwnerRepository.class);
        Shop shop = new Shop();
        shop.setName("testShopName");
        Owner owner1 = new Owner("123456", "Ivan", "Ivanovich", "Ivanov");
        owner1.increaseUseCount();
        Owner owner2 = new Owner("321654", "Vasyli", "Vasylievich", "Vasyliev");
        Card card = new Card("number123456", owner1, shop);
        Card card2 = new Card("number654321", owner2, shop);
        shopRepository.save(shop);
        ownerRepository.save(owner1);
        ownerRepository.save(owner2);
        cardRepository.save(card);
        cardRepository.save(card2);
        for (Card c:cardRepository.findAll()
             ) {
            System.out.println(c.getNumber());
        }
        List<Card> cardList = cardRepository.findByOwnerMinUseAndShop(shop);
        System.out.println(cardList.get(0).getNumber());
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
