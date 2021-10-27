package com.cardcharity.card;

import com.cardcharity.owner.Owner;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Card {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String number;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Owner getOwner() {
        return owner;
    }
}
