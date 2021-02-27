package ru.igar15.rest_voting_system.model;

public class Dish extends AbstractNamedEntity{

    private int price;
    private Menu menu;

    public Dish() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
