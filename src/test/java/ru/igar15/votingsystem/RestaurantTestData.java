package ru.igar15.votingsystem;

import ru.igar15.votingsystem.model.Restaurant;

import static ru.igar15.votingsystem.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Restaurant.class);

    public static final int RESTAURANT1_ID = START_SEQ + 2;
    public static final int RESTAURANT2_ID = START_SEQ + 3;
    public static final int NOT_FOUND = 10;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Якитория", "Новый Арбат, 22");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Бургер Кинг", "Авиамоторная, 34");

    public static Restaurant getNew() {
        return new Restaurant(null, "New", "NewAddress");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(restaurant1.getId(), "UpdatedName", "UpdatedAddress");
    }
}