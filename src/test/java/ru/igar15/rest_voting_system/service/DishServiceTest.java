package ru.igar15.rest_voting_system.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.igar15.rest_voting_system.model.Dish;
import ru.igar15.rest_voting_system.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.igar15.rest_voting_system.DishTestData.*;
import static ru.igar15.rest_voting_system.MenuTestData.MENU1_ID;
import static ru.igar15.rest_voting_system.MenuTestData.MENU2_ID;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Test
    public void create() {
        Dish created = service.create(getNew(), MENU1_ID);
        int newId = created.id();
        Dish newDish = getNew();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(newId, MENU1_ID), newDish);
    }

    @Test
    public void duplicateNameCreate() {
        assertThrows(DataAccessException.class,
                () -> service.create(new Dish(null, "Rest_1_25-02_Dish_2", 555), MENU1_ID));
    }

    @Test
    public void delete() {
        service.delete(DISH1_ID, MENU1_ID);
        assertThrows(NotFoundException.class, () -> service.get(DISH1_ID, MENU1_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, MENU1_ID));
    }

    @Test
    public void deleteFromAnotherMenu() {
        assertThrows(NotFoundException.class, () -> service.delete(DISH1_ID, MENU2_ID));
    }

    @Test
    public void get() {
        Dish dish = service.get(DISH1_ID, MENU1_ID);
        DISH_MATCHER.assertMatch(dish, dish1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, MENU1_ID));
    }

    @Test
    public void getFromAnotherMenu() {
        assertThrows(NotFoundException.class, () -> service.get(DISH1_ID, MENU2_ID));
    }

    @Test
    public void getAll() {
        List<Dish> dishes = service.getAll(MENU1_ID);
        DISH_MATCHER.assertMatch(dishes, dish2, dish1);
    }

    @Test
    public void update() {
        Dish updated = getUpdated();
        service.update(updated, MENU1_ID);
        DISH_MATCHER.assertMatch(service.get(DISH1_ID, MENU1_ID), getUpdated());
    }

    @Test
    public void updateFromAnotherMenu() {
        assertThrows(NotFoundException.class, () -> service.update(dish1, MENU2_ID));
    }
}