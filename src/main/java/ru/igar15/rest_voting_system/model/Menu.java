package ru.igar15.rest_voting_system.model;

import java.time.LocalDate;

public class Menu extends AbstractBaseEntity {

    private LocalDate date;
    private Restaurant restaurant;

    public Menu() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}