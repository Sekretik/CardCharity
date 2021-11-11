package com.cardcharity.owner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Entity
public class Owner {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private int useCount = 0;

    @Column(unique=true, nullable=false)
    @Pattern(regexp = "([0-9]){10}")
    private String passportNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String patronymic;

    public Owner(String passportNumber, String name, String surname, String patronymic) {
        this.passportNumber = passportNumber;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    protected Owner() {
    }

    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(this.getClass() !=  obj.getClass()) return false;
        return this.getPassportNumber().equals(((Owner) obj).getPassportNumber());
    }
}
