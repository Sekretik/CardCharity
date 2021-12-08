package com.cardcharity.owner;

public class OwnerWrapper {
    private String name;
    private String surname;
    private String patronymic;
    private String passportNumber;
    private boolean active;

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

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive() {
        this.active = active;
    }

    public OwnerWrapper(String name, String surname, String patronymic, String passportNumber, boolean active) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.passportNumber = passportNumber;
        this.active = active;
    }
}
