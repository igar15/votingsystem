package ru.igar15.rest_voting_system;

import ru.igar15.rest_voting_system.model.Restaurant;

import static ru.igar15.rest_voting_system.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoringFieldsComparator();

    public static final int RESTAURANT1_ID = START_SEQ + 3;
    public static final int RESTAURANT2_ID = START_SEQ + 4;
    public static final int NOT_FOUND = 10;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Rest_1", "Rest_1_Address");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Rest_2", "Rest_2_Address");

    public static Restaurant getNew() {
        return new Restaurant(null, "New_Rest", "New_Rest_Address");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(restaurant1);
        updated.setName("RestUpdatedName");
        updated.setAddress("RestUpdatedAddress");
        return updated;
    }
}
