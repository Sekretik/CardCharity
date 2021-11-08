package com.cardcharity.shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Shop {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
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
