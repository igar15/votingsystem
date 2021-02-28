package ru.igar15.rest_voting_system.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "menus")
public class Menu extends AbstractBaseEntity {

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Menu() {
    }

    public Menu(Integer id, LocalDate date) {
        super(id);
        this.date = date;
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