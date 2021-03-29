package ru.igar15.votingsystem.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.igar15.votingsystem.model.Menu;
import ru.igar15.votingsystem.model.Restaurant;
import ru.igar15.votingsystem.repository.MenuRepository;
import ru.igar15.votingsystem.repository.RestaurantRepository;
import ru.igar15.votingsystem.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuService(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @CacheEvict(value = "menusToday", key = "#restaurantId + '_' + T(java.time.LocalDate).now().toString()")
    @Transactional
    public Menu create(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        menu.setRestaurant(restaurant);
        return menuRepository.save(menu);
    }

    @CacheEvict(value = "menusToday", key = "#restaurantId + '_' + T(java.time.LocalDate).now().toString()")
    @Transactional
    public void delete(int id, int restaurantId) {
        Menu menu = get(id, restaurantId);
        menuRepository.delete(menu);
    }

    public Menu get(int id, int restaurantId) {
        return menuRepository.find(id, restaurantId).orElseThrow(() -> new NotFoundException("Not found menu with id=" + id));
    }

    public List<Menu> getAll(int restaurantId) {
        return menuRepository.findAll(restaurantId);
    }

    public Menu getByDate(int restaurantId, LocalDate date) {
        Assert.notNull(date, "date must not be null");
        return menuRepository.findByDate(restaurantId, date).orElseThrow(() -> new NotFoundException("Not found menu with date=" + date));
    }

    @Cacheable(value = "menusToday", key = "#restaurantId + '_' + T(java.time.LocalDate).now().toString()")
    public Menu getToday(int restaurantId) {
        return getByDate(restaurantId, LocalDate.now());
    }

    @CacheEvict(value = "menusToday", key = "#restaurantId + '_' + T(java.time.LocalDate).now().toString()")
    @Transactional
    public void update(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        get(menu.id(), restaurantId);
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        menu.setRestaurant(restaurant);
        menuRepository.save(menu);
    }
}