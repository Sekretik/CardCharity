package com.cardcharity.customer;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private long id;

    @NaturalId
    private String uid;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int useCount = 0;

    public Customer() {}

    public Customer(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }
}
