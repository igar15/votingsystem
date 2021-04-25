package ru.igar15.votingsystem;

import ru.igar15.votingsystem.model.Dish;
import ru.igar15.votingsystem.model.Menu;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.igar15.votingsystem.DishTestData.*;
import static ru.igar15.votingsystem.model.AbstractBaseEntity.START_SEQ;

public class MenuTestData {
    public static final TestMatcher<Menu> MENU_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Menu.class, "restaurant");

    public static final int MENU1_ID = START_SEQ + 5;
    public static final int MENU2_ID = START_SEQ + 6;
    public static final int MENU_TODAY_ID = START_SEQ + 8;

    public static final Menu menu1 = new Menu(MENU1_ID, LocalDate.of(2021, Month.FEBRUARY, 25), List.of(dish1Menu1, dish2Menu1));
    public static final Menu menu2 = new Menu(MENU2_ID, LocalDate.of(2021, Month.FEBRUARY, 26), List.of(dish1Menu2, dish2Menu2));

    public static final Menu menuToday = new Menu(MENU_TODAY_ID, LocalDate.now(), List.of(dish1MenuToday, dish2MenuToday));

    public static Menu getNew() {
        return new Menu(null, LocalDate.now(), List.of(new Dish("New1", 100), new Dish("New2", 200)));
    }
}