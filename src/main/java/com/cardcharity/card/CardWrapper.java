package com.cardcharity.card;

public class CardWrapper {
    private long id;
    private String number;
    private long shop;
    private long owner;
    private boolean active;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public long getShop() {
        return shop;
    }

    public void setShop(long shop) {
        this.shop = shop;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CardWrapper(){}

    public CardWrapper(Card card) {
        this.id = card.getId();
        this.number = card.getNumber();
        this.shop = card.getShop().getId();
        this.owner = card.getOwner().getId();
        this.active = card.isActive();
    }
}
