package ru.igar15.rest_voting_system.model;

public class Restaurant extends AbstractNamedEntity {

    private String address;

    public Restaurant() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
