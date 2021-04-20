package ru.igar15.votingsystem.util;

import ru.igar15.votingsystem.model.Menu;
import ru.igar15.votingsystem.to.MenuTo;

public class MenuUtil {
    // Suppresses default constructor, ensuring non-instantiability.
    private MenuUtil() {
    }

    public static Menu createNewFromTo(MenuTo menuTo) {
        return new Menu(menuTo.getDishes());
    }
}