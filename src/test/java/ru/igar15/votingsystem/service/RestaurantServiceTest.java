package ru.igar15.votingsystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.igar15.votingsystem.model.Restaurant;
import ru.igar15.votingsystem.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.igar15.votingsystem.RestaurantTestData.*;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void create() {
        Restaurant created = service.create(getNew());
        int newId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    void duplicateNameCreate() {
        Restaurant duplicateName = new Restaurant(null, "Якитория", "Тверская, 45", "newImageUrl");
        Restaurant created = service.create(duplicateName);
        int newId = created.id();
        duplicateName.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, duplicateName);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), duplicateName);
    }

    @Test
    void duplicateNameAddressCreate() {
        assertThrows(DataAccessException.class,
                () -> service.create(new Restaurant(null, "Якитория", "Новый Арбат, 22", "newImageUrl")));
    }

    @Test
    void delete() {
        service.delete(RESTAURANT1_ID);
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void get() {
        Restaurant restaurant = service.get(RESTAURANT1_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, restaurant1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void getAll() {
        List<Restaurant> restaurants = service.getAll();
        RESTAURANT_MATCHER.assertMatch(restaurants, restaurant3, restaurant5, restaurant6, restaurant2, restaurant4, restaurant1);
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        service.update(updated);
        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT1_ID), getUpdated());
    }

    @Test
    void createWithException() {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Restaurant(null, " ", "restaurantAddress", "newImageUrl")));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Restaurant(null, "restaurantName", " ", "newImageUrl")));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Restaurant(null, "restaurantName", "restaurantAddress", " ")));
    }
}