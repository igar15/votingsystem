package ru.igar15.votingsystem.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.igar15.votingsystem.model.Menu;
import ru.igar15.votingsystem.model.Restaurant;
import ru.igar15.votingsystem.repository.MenuRepository;
import ru.igar15.votingsystem.to.MenuTo;
import ru.igar15.votingsystem.util.exception.NotFoundException;

import java.time.LocalDate;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantService restaurantService;

    public MenuService(MenuRepository menuRepository, RestaurantService restaurantService) {
        this.menuRepository = menuRepository;
        this.restaurantService = restaurantService;
    }

    @CacheEvict(value = "menusToday", key = "#restaurantId + '_' + T(java.time.LocalDate).now().toString()")
    @Transactional
    public Menu create(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        Restaurant restaurant = restaurantService.get(restaurantId);
        menu.setRestaurant(restaurant);
        return menuRepository.save(menu);
    }
    @CacheEvict(value = "menusToday", key = "#restaurantId + '_' + T(java.time.LocalDate).now().toString()")
    public void deleteToday(int restaurantId) {
        restaurantService.get(restaurantId);
        Menu menu = getByDate(restaurantId, LocalDate.now());
        menuRepository.delete(menu);
    }

    public Menu getByDate(int restaurantId, LocalDate date) {
        restaurantService.get(restaurantId);
        Assert.notNull(date, "date must not be null");
        return menuRepository.findByDate(restaurantId, date).orElseThrow(() -> new NotFoundException("Not found menu with date=" + date));
    }

    @Cacheable(value = "menusToday", key = "#restaurantId + '_' + T(java.time.LocalDate).now().toString()")
    public Menu getToday(int restaurantId) {
        return getByDate(restaurantId, LocalDate.now());
    }

    @CacheEvict(value = "menusToday", key = "#restaurantId + '_' + T(java.time.LocalDate).now().toString()")
    @Transactional
    public void updateToday(MenuTo menuTo, int restaurantId) {
        restaurantService.get(restaurantId);
        Assert.notNull(menuTo, "menuTo must not be null");
        Menu menu = getByDate(restaurantId, LocalDate.now());
        menu.setDishes(menuTo.getDishes());
    }
}