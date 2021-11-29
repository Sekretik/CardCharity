package com.cardcharity.history;

import com.cardcharity.card.Card;
import com.cardcharity.customer.Customer;

import javax.persistence.*;
import java.util.Date;

@Entity
public class History {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public History(Date date, Card card, Customer customer) {
        this.date = date;
        this.card = card;
        this.customer = customer;
    }

    public History() {}
}