package ru.igar15.votingsystem.to;

import ru.igar15.votingsystem.model.Dish;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class MenuTo {

    @NotEmpty
    @Valid
    private List<Dish> dishes;

    public MenuTo() {
    }

    public MenuTo(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public String toString() {
        return "MenuTo{" +
                "dishes=" + dishes +
                '}';
    }
}