package ru.igar15.votingsystem.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.igar15.votingsystem.RestaurantTestData;
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
import static ru.igar15.votingsystem.MenuTestData.*;
import static ru.igar15.votingsystem.RestaurantTestData.RESTAURANT1_ID;
import static ru.igar15.votingsystem.RestaurantTestData.RESTAURANT2_ID;
import static ru.igar15.votingsystem.TestUtil.readFromJson;
import static ru.igar15.votingsystem.TestUtil.userHttpBasic;
import static ru.igar15.votingsystem.UserTestData.admin;
import static ru.igar15.votingsystem.UserTestData.user;
import static ru.igar15.votingsystem.util.exception.ErrorType.*;
import static ru.igar15.votingsystem.web.AppExceptionHandler.EXCEPTION_DUPLICATE_DISH;
import static ru.igar15.votingsystem.web.AppExceptionHandler.EXCEPTION_DUPLICATE_MENU;

class MenuRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL_RESTAURANT1 = "/rest/restaurants/" + RESTAURANT1_ID + "/menus";
    private static final String REST_URL_RESTAURANT2 = "/rest/restaurants/" + RESTAURANT2_ID + "/menus";
    private static final String BAD_RESTAURANT_REST_URL = "/rest/restaurants/" + RestaurantTestData.NOT_FOUND + "/menus";

    @Autowired
    private MenuService menuService;

    @Test
    void getToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANT2))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menuToday));
    }

    @Test
    void getTodayNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANT1))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND));
    }

    @Test
    void getTodayBadRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(BAD_RESTAURANT_REST_URL))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND));
    }

    @Test
    void deleteToday() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_RESTAURANT2)
                .with(userHttpBasic(admin)))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertThrows(NotFoundException.class, () -> menuService.getToday(RESTAURANT2_ID));
    }

    @Test
    void deleteTodayNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_RESTAURANT1)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND));
    }

    @Test
    void deleteTodayBadRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.delete(BAD_RESTAURANT_REST_URL)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND));
    }

    @Test
    void deleteTodayForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_RESTAURANT2)
                .with(userHttpBasic(user)))
                .andExpect(status().isForbidden())
                .andExpect(errorType(ACCESS_DENIED_ERROR));
    }

    @Test
    void deleteTodayUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_RESTAURANT2))
                .andExpect(status().isUnauthorized())
                .andExpect(errorType(UNAUTHORIZED_ERROR));
    }

    @Test
    void createTodayWithLocation() throws Exception {
        MenuTo newTo = getNewMenuTo();
        Menu newMenu = MenuUtil.createNewTodayFromTo(newTo);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANT1)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andExpect(status().isCreated());

        Menu created = readFromJson(action, Menu.class);
        int newId = created.getId();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(menuService.getToday(RESTAURANT1_ID), newMenu);
    }

    @Test
    void createTodayForbidden() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANT1)
                .with(userHttpBasic(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNewMenuTo())))
                .andExpect(status().isForbidden())
                .andExpect(errorType(ACCESS_DENIED_ERROR));
    }

    @Test
    void createTodayUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANT1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNewMenuTo())))
                .andExpect(status().isUnauthorized())
                .andExpect(errorType(UNAUTHORIZED_ERROR));
    }

    @Test
    void createTodayBadRestaurant() throws Exception {
        MenuTo newTo = getNewMenuTo();
        perform(MockMvcRequestBuilders.post(BAD_RESTAURANT_REST_URL)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND));
    }

    @Test
    void updateToday() throws Exception {
        MenuTo updatedTo = getUpdatedMenuTo();
        perform(MockMvcRequestBuilders.put(REST_URL_RESTAURANT2)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isNoContent());

        MENU_MATCHER.assertMatch(menuService.getToday(RESTAURANT2_ID), new Menu(MENU_TODAY_ID, LocalDate.now(), updatedTo.getDishes()));
    }

    @Test
    void updateTodayNotFound() throws Exception {
        MenuTo updatedTo = getUpdatedMenuTo();
        perform(MockMvcRequestBuilders.put(REST_URL_RESTAURANT1)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND));
    }

    @Test
    void updateTodayForbidden() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL_RESTAURANT2)
                .with(userHttpBasic(user))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdatedMenuTo())))
                .andExpect(status().isForbidden())
                .andExpect(errorType(ACCESS_DENIED_ERROR));
    }

    @Test
    void updateTodayUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL_RESTAURANT2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getUpdatedMenuTo())))
                .andExpect(status().isUnauthorized())
                .andExpect(errorType(UNAUTHORIZED_ERROR));
    }

    @Test
    void updateTodayBadRestaurant() throws Exception {
        MenuTo updatedTo = getUpdatedMenuTo();
        perform(MockMvcRequestBuilders.put(BAD_RESTAURANT_REST_URL)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(DATA_NOT_FOUND));
    }

    @Test
    void createTodayInvalid() throws Exception {
        MenuTo newTo = new MenuTo(List.of());
        perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANT1)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void createTodayInvalidDishes() throws Exception {
        MenuTo newTo = new MenuTo(List.of(new Dish("", 100)));
        perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANT1)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateTodayInvalid() throws Exception {
        MenuTo updatedTo = new MenuTo(null);
        perform(MockMvcRequestBuilders.put(REST_URL_RESTAURANT2)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createTodayDuplicateDate() throws Exception {
        MenuTo newTo = getNewMenuTo();
        perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANT2)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_MENU));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createTodayDuplicateDish() throws Exception {
        MenuTo newTo = new MenuTo(List.of(new Dish("dish1", 100), new Dish("dish1", 200)));
        perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANT1)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_DISH));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateTodayDuplicateDish() throws Exception {
        MenuTo updatedTo = new MenuTo(List.of(new Dish("dish1", 100), new Dish("dish1", 200)));
        perform(MockMvcRequestBuilders.put(REST_URL_RESTAURANT2)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_DISH));
    }

    private MenuTo getNewMenuTo() {
        return new MenuTo(List.of(new Dish("dish1", 50), new Dish("dish2", 100)));
    }

    private MenuTo getUpdatedMenuTo() {
        return new MenuTo(List.of(new Dish("updated1", 50), new Dish("updated2", 100)));
    }
}