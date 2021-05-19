package ru.igar15.votingsystem.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.igar15.votingsystem.model.Restaurant;
import ru.igar15.votingsystem.repository.RestaurantRepository;
import ru.igar15.votingsystem.util.exception.NotFoundException;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @Caching(evict = {
            @CacheEvict(value = "restaurants", allEntries = true),
            @CacheEvict(value = "menusToday", key = "#id + '_' + T(java.time.LocalDate).now().toString()")
    })
    public void delete(int id) {
        Restaurant restaurant = get(id);
        repository.delete(restaurant);
    }

    public Restaurant get(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not found restaurant with id=" + id));
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return repository.findAllByOrderByNameAscAddressAsc();
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        get(restaurant.id());
        repository.save(restaurant);
    }

    public List<Restaurant> getAllByName(String name) {
        Assert.notNull(name, "name must not be null");
        return repository.findAllByNameContainingIgnoreCaseOrderByNameAscAddressAsc(name);
    }
}