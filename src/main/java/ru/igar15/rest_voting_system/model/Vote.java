package ru.igar15.rest_voting_system.model;

import java.time.LocalDateTime;

public class Vote extends AbstractBaseEntity {

    private LocalDateTime dateTime;
    private User user;
    private Restaurant restaurant;

    public Vote() {
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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
