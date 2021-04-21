package ru.igar15.votingsystem.util;

import ru.igar15.votingsystem.model.Menu;
import ru.igar15.votingsystem.to.MenuTo;

import java.time.LocalDate;

public class MenuUtil {
    // Suppresses default constructor, ensuring non-instantiability.
    private MenuUtil() {
    }

    public static Menu createNewTodayFromTo(MenuTo menuTo) {
        return new Menu(null, LocalDate.now(), menuTo.getDishes());
    }
}