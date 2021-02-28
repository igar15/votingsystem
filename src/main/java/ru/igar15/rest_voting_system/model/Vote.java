package ru.igar15.rest_voting_system.model;

import java.time.LocalDate;

public class Vote extends AbstractBaseEntity {

    private LocalDate date;
    private User user;
    private Restaurant restaurant;

    public Vote() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}