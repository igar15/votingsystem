package ru.igar15.rest_voting_system;

import ru.igar15.rest_voting_system.model.Dish;
import ru.igar15.rest_voting_system.model.Menu;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.igar15.rest_voting_system.DishTestData.*;
import static ru.igar15.rest_voting_system.model.AbstractBaseEntity.START_SEQ;

public class MenuTestData {
    public static final TestMatcher<Menu> MENU_MATCHER = TestMatcher.usingIgnoringFieldsComparator("restaurant");

    public static final int MENU1_ID = START_SEQ + 5;
    public static final int MENU2_ID = START_SEQ + 6;
    public static final int NOT_FOUND = 10;

    public static final Menu menu1 = new Menu(MENU1_ID, LocalDate.of(2021, Month.FEBRUARY, 25), List.of(dish1Menu1, dish2Menu1));
    public static final Menu menu2 = new Menu(MENU2_ID, LocalDate.of(2021, Month.FEBRUARY, 26), List.of(dish1Menu2, dish2Menu2));

    public static Menu getNew() {
        Menu menu = new Menu();
        menu.setDishes(List.of(new Dish("dishName1", 100), new Dish("dishName2", 200)));
        return menu;
    }

    public static Menu getUpdated() {
        return new Menu(MENU1_ID, LocalDate.of(2021, Month.MARCH, 9), List.of(dish1Menu1, new Dish("dish3Menu1", 500)));
    }
}