package ru.igar15.votingsystem.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.igar15.votingsystem.MenuTestData;
import ru.igar15.votingsystem.model.Dish;
import ru.igar15.votingsystem.model.Menu;
import ru.igar15.votingsystem.service.MenuService;
import ru.igar15.votingsystem.to.MenuTo;
import ru.igar15.votingsystem.util.MenuUtil;
import ru.igar15.votingsystem.util.exception.NotFoundException;
import ru.igar15.votingsystem.web.json.JsonUtil;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.igar15.votingsystem.MenuTestData.MENU_MATCHER;
import static ru.igar15.votingsystem.RestaurantTestData.RESTAURANT1_ID;
import static ru.igar15.votingsystem.TestUtil.readFromJson;
import static ru.igar15.votingsystem.TestUtil.userHttpBasic;
import static ru.igar15.votingsystem.UserTestData.admin;
import static ru.igar15.votingsystem.UserTestData.user;

class MenuRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = "/rest/restaurants/" + RESTAURANT1_ID + "/menus/today";

    @Autowired
    private MenuService menuService;

    @Test
    void getToday() throws Exception {
        Menu createdToday = createTodayMenu();
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(createdToday));
    }

    @Test
    void deleteToday() throws Exception {
        Menu createdToday = createTodayMenu();
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertThrows(NotFoundException.class, () -> menuService.get(createdToday.id(), RESTAURANT1_ID));
    }

    @Test
    void deleteTodayNotAdmin() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteTodayUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createTodayWithLocation() throws Exception {
        MenuTo newTo = getNewMenuTo();
        Menu newMenu = MenuUtil.createNewFromTo(newTo);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andExpect(status().isCreated());

        Menu created = readFromJson(action, Menu.class);
        int newId = created.getId();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(menuService.get(newId, RESTAURANT1_ID), newMenu);
        MENU_MATCHER.assertMatch(menuService.getToday(RESTAURANT1_ID), newMenu);
    }

    @Test
    void createTodayNotAdmin() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNewMenuTo())))
                .andExpect(status().isForbidden());
    }

    @Test
    void createTodayUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNewMenuTo())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateToday() throws Exception {
        Menu createdToday = createTodayMenu();
        MenuTo updatedTo = getUpdatedMenuTo();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isNoContent());

        MENU_MATCHER.assertMatch(menuService.get(createdToday.getId(), RESTAURANT1_ID), new Menu(createdToday.id(), LocalDate.now(), updatedTo.getDishes()));
    }

    @Test
    void updateTodayNotAdmin() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL)
                .with(userHttpBasic(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdatedMenuTo())))
                .andExpect(status().isForbidden());
    }

    @Test
    void updateTodayUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdatedMenuTo())))
                .andExpect(status().isUnauthorized());
    }

    private Menu createTodayMenu() {
        return menuService.create(MenuTestData.getNew(), RESTAURANT1_ID);
    }

    private MenuTo getNewMenuTo() {
        return new MenuTo(List.of(new Dish("dish1", 50), new Dish("dish2", 100)));
    }

    private MenuTo getUpdatedMenuTo() {
        return new MenuTo(List.of(new Dish("updated1", 50), new Dish("updated2", 100)));
    }
}