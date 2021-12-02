package com.cardcharity.card;

import com.cardcharity.owner.Owner;
import com.cardcharity.shop.Shop;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@EntityListeners(CardListener.class)
@Entity
public class Card {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String number;

    @ManyToOne
    @JoinColumn(name = "shop_id",nullable = false)
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "owner_id",nullable = false)
    private Owner owner;

    @Column(nullable = false)
    private boolean active = true;

    public Card() {

    }

    public Card(String number, Owner owner, Shop shop) {
        this.number = number;
        this.owner = owner;
        this.shop = shop;
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

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(this.getClass() !=  obj.getClass()) return false;
        return (this.getNumber().equals(((Card) obj).getNumber()) && (this.getShop() == ((Card) obj).getShop()));
    }
}
