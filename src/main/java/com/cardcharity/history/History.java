package com.cardcharity.history;

import com.cardcharity.card.Card;
import com.cardcharity.user.Customer;

import javax.persistence.*;
import java.util.Date;

@Entity
public class History {
    @Id
    @GeneratedValue
    private long id;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne
    //@JoinColumn(name = "user_id")
    private Customer customer;

    private long getId() {
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

    public Customer getUser() {
        return customer;
    }

    public void setUser(Customer customer) {
        this.customer = customer;
    }

    public History(Date date, Card card, Customer customer) {
        this.date = date;
        this.card = card;
        this.customer = customer;
    }

    public History() {}
}