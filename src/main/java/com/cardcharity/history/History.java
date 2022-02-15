package com.cardcharity.history;

import com.cardcharity.card.Card;
import com.cardcharity.customer.Customer;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class History {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "card_id",nullable = false)
    private Card card;

    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;

    public long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public History(LocalDate date, Card card, Customer customer) {
        this.date = date;
        this.card = card;
        this.customer = customer;
    }

    public History() {}
}