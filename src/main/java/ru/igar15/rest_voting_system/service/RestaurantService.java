package ru.igar15.rest_voting_system.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.igar15.rest_voting_system.model.Restaurant;
import ru.igar15.rest_voting_system.repository.RestaurantRepository;
import ru.igar15.rest_voting_system.util.exception.NotFoundException;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
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
    @Transactional
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        get(restaurant.id());
        repository.save(restaurant);
    }
}