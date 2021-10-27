package com.cardcharity.card;

import com.cardcharity.owner.Owner;
import com.cardcharity.shop.Shop;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Card {
    @Id
    @GeneratedValue
    @NotNull
    private long id;

    @NotNull
    private String number;

    @NotNull
    @ManyToOne
    private Shop shop;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @NotNull
    private boolean active = true;

    public Card() {

    }

    public Card(String number, Owner owner, Shop shop) {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Owner getOwner() {
        return owner;
    }
}
