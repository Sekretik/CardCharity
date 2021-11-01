package com.cardcharity.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private long use_count;

    public User(long use_count) {
        this.use_count = use_count;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUse_count() {
        return use_count;
    }

    public void setUse_count(long use_count) {
        this.use_count = use_count;
    }
}
