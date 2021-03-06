package ru.igar15.rest_voting_system.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.igar15.rest_voting_system.model.User;
import ru.igar15.rest_voting_system.repository.UserRepository;
import ru.igar15.rest_voting_system.util.exception.NotFoundException;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void delete(int id) {
        User user = get(id);
        repository.delete(user);
    }

    public User get(int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not found user with id=" + id));
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new NotFoundException("Not found user with email=" + email));
    }

    @Cacheable("users")
    public List<User> getAll() {
        return repository.findAllByOrderByNameAscEmailAsc();
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        get(user.id());
        repository.save(user);
    }
}