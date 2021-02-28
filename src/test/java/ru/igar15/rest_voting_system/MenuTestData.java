package ru.igar15.rest_voting_system;

import ru.igar15.rest_voting_system.model.Menu;

import java.time.LocalDate;
import java.time.Month;

import static ru.igar15.rest_voting_system.model.AbstractBaseEntity.START_SEQ;

public class MenuTestData {
    public static final TestMatcher<Menu> MENU_MATCHER = TestMatcher.usingIgnoringFieldsComparator("restaurant");

    public static final int REST1_MENU1_ID = START_SEQ + 5;
    public static final int REST1_MENU2_ID = START_SEQ + 6;
    public static final int REST2_MENU1_ID = START_SEQ + 7;
    public static final int NOT_FOUND = 10;

    public static final Menu rest1Menu1 = new Menu(REST1_MENU1_ID, LocalDate.of(2021, Month.FEBRUARY, 25));
    public static final Menu rest1Menu2 = new Menu(REST1_MENU2_ID, LocalDate.of(2021, Month.FEBRUARY, 26));

    public static Menu getNew() {
        return new Menu(null, LocalDate.of(2021, Month.MARCH, 8));
    }

    public static Menu getUpdated() {
        return new Menu(REST1_MENU1_ID, LocalDate.of(2021, Month.MARCH, 9));
    }
}