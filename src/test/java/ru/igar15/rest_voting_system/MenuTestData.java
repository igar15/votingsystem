package ru.igar15.rest_voting_system;

import ru.igar15.rest_voting_system.model.Menu;

import java.time.LocalDate;
import java.time.Month;

import static ru.igar15.rest_voting_system.model.AbstractBaseEntity.START_SEQ;

public class MenuTestData {
    public static final TestMatcher<Menu> MENU_MATCHER = TestMatcher.usingIgnoringFieldsComparator("restaurant");

    public static final int MENU1_ID = START_SEQ + 5;
    public static final int MENU2_ID = START_SEQ + 6;
    public static final int NOT_FOUND = 10;

    public static final Menu menu1 = new Menu(MENU1_ID, LocalDate.of(2021, Month.FEBRUARY, 25), true);
    public static final Menu menu2 = new Menu(MENU2_ID, LocalDate.of(2021, Month.FEBRUARY, 26), true);

    public static Menu getNew() {
        return new Menu();
    }

    public static Menu getUpdated() {
        return new Menu(MENU1_ID, LocalDate.of(2021, Month.MARCH, 9), true);
    }

    public static Menu getChangedPublishedStatus(boolean published) {
        return new Menu(MENU1_ID, LocalDate.of(2021, Month.FEBRUARY, 25), published);
    }
}