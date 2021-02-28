package ru.igar15.rest_voting_system.service;

import ru.igar15.rest_voting_system.TestMatcher;
import ru.igar15.rest_voting_system.model.Role;
import ru.igar15.rest_voting_system.model.User;

import java.util.Collections;
import java.util.Date;

import static ru.igar15.rest_voting_system.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingIgnoringFieldsComparator("registered", "roles");

    public static final int USER1_ID = START_SEQ;
    public static final int USER2_ID = START_SEQ + 1;
    public static final int ADMIN_ID = START_SEQ + 2;
    public static final int NOT_FOUND = 10;

    public static final User user1 = new User(USER1_ID, "User_1", "user1@yandex.ru", "password", Role.USER);
    public static final User user2 = new User(USER2_ID, "User_2", "user2@yandex.ru", "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(user1);
        updated.setEmail("update@gmail.com");
        updated.setName("UpdatedName");
        updated.setPassword("newPass");
        updated.setEnabled(false);
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }
}
