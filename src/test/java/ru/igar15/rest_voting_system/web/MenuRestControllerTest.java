package ru.igar15.rest_voting_system.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.igar15.rest_voting_system.MenuTestData;
import ru.igar15.rest_voting_system.model.Menu;
import ru.igar15.rest_voting_system.service.MenuService;
import ru.igar15.rest_voting_system.util.exception.NotFoundException;
import ru.igar15.rest_voting_system.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.igar15.rest_voting_system.MenuTestData.*;
import static ru.igar15.rest_voting_system.RestaurantTestData.RESTAURANT1_ID;
import static ru.igar15.rest_voting_system.TestUtil.readFromJson;

class MenuRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MenuRestController.REST_URL + '/';

    @Autowired
    private MenuService menuService;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + "/menus"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu2, menu1));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + "/menus/" + MENU1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu1));
    }

    @Test
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + "/menus/by")
                .param("date", "2021-02-25"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu1));
    }

    @Test
    void getToday() throws Exception {
        Menu createdToday = menuService.create(MenuTestData.getNew(), RESTAURANT1_ID);
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID + "/menus/today"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(createdToday));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT1_ID + "/menus/" + MENU1_ID))
                .andExpect(status().isNoContent())
                .andDo(print());
        assertThrows(NotFoundException.class, () -> menuService.get(MENU1_ID, RESTAURANT1_ID));
    }

    @Test
    void createWithLocation() throws Exception {
        Menu newMenu = MenuTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT1_ID + "/menus")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)))
                .andExpect(status().isCreated());

        Menu created = readFromJson(action, Menu.class);
        int newId = created.id();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(menuService.get(newId, RESTAURANT1_ID), newMenu);
        MENU_MATCHER.assertMatch(menuService.getToday(RESTAURANT1_ID), newMenu);
    }

    @Test
    void update() throws Exception {
        Menu updated = MenuTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT1_ID + "/menus/" + MENU1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MENU_MATCHER.assertMatch(menuService.get(MENU1_ID, RESTAURANT1_ID), updated);
    }
}