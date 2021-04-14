package ru.igar15.votingsystem.web.json;

import org.junit.jupiter.api.Test;
import ru.igar15.votingsystem.model.Menu;

import java.util.List;

import static ru.igar15.votingsystem.MenuTestData.*;

class JsonUtilTest {

    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(menu1);
        System.out.println(json);
        Menu menu = JsonUtil.readValue(json, Menu.class);
        MENU_MATCHER.assertMatch(menu, menu1);
    }

    @Test
    void readWriteValues() {
        String json = JsonUtil.writeValue(List.of(menu1, menu2));
        System.out.println(json);
        List<Menu> menus = JsonUtil.readValues(json, Menu.class);
        MENU_MATCHER.assertMatch(menus, List.of(menu1, menu2));
    }
}