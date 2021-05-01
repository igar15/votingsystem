package ru.igar15.votingsystem;

import ru.igar15.votingsystem.model.Restaurant;

import static ru.igar15.votingsystem.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Restaurant.class);

    public static final int RESTAURANT1_ID = START_SEQ + 2;
    public static final int RESTAURANT2_ID = START_SEQ + 3;
    public static final int RESTAURANT3_ID = START_SEQ + 4;
    public static final int RESTAURANT4_ID = START_SEQ + 14;
    public static final int RESTAURANT5_ID = START_SEQ + 15;
    public static final int RESTAURANT6_ID = START_SEQ + 16;
    public static final int NOT_FOUND = 10;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Якитория", "Новый Арбат, 22", "assets/images/yakitoriya.jpeg");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Бургер Кинг", "Авиамоторная, 34", "assets/images/burger-king.jpeg");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Paulaner", "Невский, 89", "assets/images/paulaner.jpg");
    public static final Restaurant restaurant4 = new Restaurant(RESTAURANT4_ID, "Орда", "Мясницкая, 43, стр. 2", "assets/images/orda.jpg");
    public static final Restaurant restaurant5 = new Restaurant(RESTAURANT5_ID, "Plov Project", "Малая Дмитровка, 20", "assets/images/plov-project.jpg");
    public static final Restaurant restaurant6 = new Restaurant(RESTAURANT6_ID, "Venting Cafe", "Цветной бульвар, 34", "assets/images/venting-cafe.jpg");

    public static Restaurant getNew() {
        return new Restaurant(null, "New", "NewAddress", "newImageUrl");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "UpdatedName", "UpdatedAddress", "updatedImageUrl");
    }
}