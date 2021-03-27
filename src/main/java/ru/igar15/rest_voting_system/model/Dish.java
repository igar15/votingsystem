package ru.igar15.rest_voting_system.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Embeddable
public class Dish {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name")
    private String name;

    @Min(1)
    @Column(name = "price")
    private int price;

    public Dish() {
    }

    public Dish(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return price == dish.price &&
                Objects.equals(name, dish.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        return "Dish{" +
                "name=" + name +
                ", price=" + price +
                '}';
    }
}