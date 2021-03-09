package ru.igar15.rest_voting_system.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.igar15.rest_voting_system.model.Dish;
import ru.igar15.rest_voting_system.model.Menu;
import ru.igar15.rest_voting_system.repository.DishRepository;
import ru.igar15.rest_voting_system.repository.MenuRepository;
import ru.igar15.rest_voting_system.util.exception.NotFoundException;

import java.util.List;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final MenuRepository menuRepository;

    public DishService(DishRepository dishRepository, MenuRepository menuRepository) {
        this.dishRepository = dishRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public Dish create(Dish dish, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        Menu menu = menuRepository.getOne(menuId);
        dish.setMenu(menu);
        return dishRepository.save(dish);
    }

    @Transactional
    public void delete(int id, int menuId) {
        Dish dish = get(id, menuId);
        dishRepository.delete(dish);
    }

    public Dish get(int id, int menuId) {
        return dishRepository.find(id, menuId).orElseThrow(() -> new NotFoundException("Not found dish with id=" + id));
    }

    public List<Dish> getAll(int menuId) {
        return dishRepository.findAll(menuId);
    }

    @Transactional
    public void update(Dish dish, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        get(dish.id(), menuId);
        Menu menu = menuRepository.getOne(menuId);
        dish.setMenu(menu);
        dishRepository.save(dish);
    }
}