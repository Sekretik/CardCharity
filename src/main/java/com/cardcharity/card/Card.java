package com.cardcharity.card;

import com.cardcharity.owner.Owner;
import com.cardcharity.shop.Shop;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
public class Card {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String cardNumber;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(nullable = false)
    private boolean active = true;

    public Card() {

    }

    public Card(String number, Owner owner, Shop shop) {
        this.cardNumber = number;
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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
        return (this.getCardNumber().equals(((Card) obj).getCardNumber()) && (this.getShop() == ((Card) obj).getShop()));
    }
}
