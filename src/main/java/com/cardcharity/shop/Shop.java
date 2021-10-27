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

    @NotNull
    @NaturalId
    private String name;

    private Shop() {}

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
