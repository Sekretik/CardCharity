package com.cardcharity.history;

import java.util.Date;

public class HistoryWrapper {
    private long id;
    private Date date;
    private long card;
    private long customer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getCard() {
        return card;
    }

    public void setCard(long card) {
        this.card = card;
    }

    public long getCustomer() {
        return customer;
    }

    public void setCustomer(long customer) {
        this.customer = customer;
    }

    public HistoryWrapper(History history) {
        this.id = history.getId();
        this.date = history.getDate();
        this.card = history.getCard().getId();
        this.customer = history.getCustomer().getId();
    }
}
