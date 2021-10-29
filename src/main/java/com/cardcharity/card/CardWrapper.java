package com.cardcharity.card;

public class CardWrapper {
    private String cardNumber;
    private long shopId;
    private long ownerId;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public CardWrapper(String cardNumber, long shopId, long ownerId) {
        this.cardNumber = cardNumber;
        this.shopId = shopId;
        this.ownerId = ownerId;
    }
}
