package com.cardcharity.card;

public class CardWrapper {
    private String cardNumber;
    private long shop;
    private long owner;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

    public CardWrapper(String cardNumber, long shopId, long owner) {
        this.cardNumber = cardNumber;
        this.shop = shopId;
        this.owner = owner;
    }
}
