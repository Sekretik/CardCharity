package com.cardcharity.shop;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;

@Entity
public class Shop {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false, unique=true)
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) { this.id = id; }

    public Shop() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
