package ru.igar15.votingsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.igar15.votingsystem.model.Dish;
import ru.igar15.votingsystem.model.Menu;
import ru.igar15.votingsystem.to.MenuTo;
import ru.igar15.votingsystem.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.igar15.votingsystem.MenuTestData.*;
import static ru.igar15.votingsystem.RestaurantTestData.NOT_FOUND;
import static ru.igar15.votingsystem.RestaurantTestData.RESTAURANT1_ID;

public class MenuServiceTest extends AbstractServiceTest {

    @Autowired
    private MenuService service;

    @Test
    void create() {
        Menu created = service.create(getNew(), RESTAURANT1_ID);
        int newId = created.id();
        Menu newMenu = getNew();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(service.getToday(RESTAURANT1_ID), newMenu);
    }

    @Test
    void createBadRestaurant() {
        assertThrows(NotFoundException.class, () -> service.create(getNew(), NOT_FOUND));
    }

    @Test
    void duplicateDateCreate() {
        Menu menu = getNew();
        menu.setDate(menu1.getDate());
        assertThrows(DataAccessException.class,
                () -> service.create(menu, RESTAURANT1_ID));
    }

    @Test
    void duplicateDishNameCreate() {
        Menu menu = getNew();
        menu.setDishes(List.of(new Dish("name", 100), new Dish("name", 200)));
        assertThrows(DataAccessException.class,
                () -> service.create(menu, RESTAURANT1_ID));
    }

    @Test
    void deleteToday() {
        service.create(getNew(), RESTAURANT1_ID);
        service.deleteToday(RESTAURANT1_ID);
        assertThrows(NotFoundException.class, () -> service.getToday(RESTAURANT1_ID));
    }

    @Test
    void deleteTodayNotFound() {
        assertThrows(NotFoundException.class, () -> service.deleteToday(RESTAURANT1_ID));
    }

    @Test
    void deleteTodayBadRestaurant() {
        assertThrows(NotFoundException.class, () -> service.deleteToday(NOT_FOUND));
    }

    @Test
    void getByDate() {
        Menu menu = service.getByDate(RESTAURANT1_ID, LocalDate.of(2021, Month.FEBRUARY, 25));
        MENU_MATCHER.assertMatch(menu, menu1);
    }

    @Test
    void getByDateNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByDate(RESTAURANT1_ID, LocalDate.of(2021, Month.FEBRUARY, 20)));
    }

    @Test
    void getByDateBadRestaurant() {
        assertThrows(NotFoundException.class, () -> service.getByDate(NOT_FOUND, LocalDate.of(2021, Month.FEBRUARY, 25)));
    }

    @Test
    void getToday() {
        Menu createdToday = service.create(getNew(), RESTAURANT1_ID);
        int newId = createdToday.id();
        Menu newMenu = getNew();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(service.getToday(RESTAURANT1_ID), newMenu);
    }

    @Test
    void getTodayNotFound() {
        assertThrows(NotFoundException.class, () -> service.getToday(RESTAURANT1_ID));
    }

    @Test
    void getTodayBadRestaurant() {
        assertThrows(NotFoundException.class, () -> service.getToday(NOT_FOUND));
    }

    @Test
    void updateToday() {
        Menu createdToday = service.create(getNew(), RESTAURANT1_ID);
        MenuTo updatedTo = new MenuTo(List.of(new Dish("updated1", 50), new Dish("updated2", 100)));
        service.updateToday(updatedTo, RESTAURANT1_ID);
        MENU_MATCHER.assertMatch(service.getToday(RESTAURANT1_ID), new Menu(createdToday.id(), LocalDate.now(), updatedTo.getDishes()));
    }

    @Test
    void updateTodayBadRestaurant() {
        MenuTo updatedTo = new MenuTo(List.of(new Dish("updated1", 50), new Dish("updated2", 100)));
        assertThrows(NotFoundException.class, () -> service.updateToday(updatedTo, NOT_FOUND));
    }

    @Test
    void createWithException() {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Menu(null, LocalDate.now(), null), RESTAURANT1_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Menu(null, LocalDate.now(), List.of(new Dish("", 300))), RESTAURANT1_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Menu(null, LocalDate.now(), List.of(new Dish("dish1", 0))), RESTAURANT1_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Menu(null, LocalDate.now(), List.of()), RESTAURANT1_ID));
    }
}