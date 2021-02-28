package ru.igar15.rest_voting_system.service;

import org.springframework.stereotype.Service;
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

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.save(user);
    }

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

    public List<User> getAll() {
        return repository.findAllByOrderByNameAscEmailAsc();
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        get(user.id());
        repository.save(user);
    }
}