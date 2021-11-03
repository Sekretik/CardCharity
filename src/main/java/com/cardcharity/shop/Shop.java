package com.cardcharity.shop;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Shop {
    @Id
    @GeneratedValue
    @NotNull
    private long id;

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Shop() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
