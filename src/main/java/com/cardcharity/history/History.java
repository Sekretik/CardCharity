package com.cardcharity.history;

import com.cardcharity.card.Card;
import com.cardcharity.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class History {
    @Id
    @GeneratedValue
    private long id;

    private Date date;

    private Card card;

    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public History(Date date, Card card, User user) {
        this.date = date;
        this.card = card;
        this.user = user;
    }
}