package ru.igar15.rest_voting_system;

import ru.igar15.rest_voting_system.model.Dish;

import static ru.igar15.rest_voting_system.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static final TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingIgnoringFieldsComparator("menu");

    public static final int DISH1_ID = START_SEQ + 9;
    public static final int DISH2_ID = START_SEQ + 10;
    public static final int NOT_FOUND = 10;

    public static final Dish dish1 = new Dish(DISH1_ID, "Rest_1_25-02_Dish_2", 250);
    public static final Dish dish2 = new Dish(DISH2_ID, "Rest_1_25-02_Dish_1", 300);

    public static Dish getNew() {
        return new Dish(null, "New_Dish", 777);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "Updated_dish_name", 444);
    }
}