package com.cardcharity.history;

import java.time.LocalDate;
import java.util.Date;

public class HistoryWrapper {
    private Long id;
    private LocalDate date;
    private Long card;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getCard() {
        return card;
    }

    public void setCard(Long card) {
        this.card = card;
    }

    public Long getCustomer() {
        return customer;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    private Long customer;

    public HistoryWrapper(History history) {
        this.id = history.getId();
        this.date = history.getDate();
        this.card = history.getCard().getId();
        this.customer = history.getCustomer().getId();
    }
}
