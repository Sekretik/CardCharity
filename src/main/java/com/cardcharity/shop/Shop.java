package com.cardcharity.shop;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;

@Entity
public class Shop {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @UniqueElements
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public Shop() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
