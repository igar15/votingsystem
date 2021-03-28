package ru.igar15.rest_voting_system.web.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.igar15.rest_voting_system.UserTestData;
import ru.igar15.rest_voting_system.model.User;
import ru.igar15.rest_voting_system.service.UserService;
import ru.igar15.rest_voting_system.web.AbstractControllerTest;
import ru.igar15.rest_voting_system.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.igar15.rest_voting_system.UserTestData.*;
import static ru.igar15.rest_voting_system.web.user.ProfileRestController.*;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    private UserService userService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(USER_MATCHER.contentJson(user));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL))
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(userService.getAll(), admin);
    }

    @Test
    void update() throws Exception {
        User updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        USER_MATCHER.assertMatch(userService.get(USER_ID), updated);
    }
}