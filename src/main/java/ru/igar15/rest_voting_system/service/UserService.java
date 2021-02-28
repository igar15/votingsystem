package ru.igar15.rest_voting_system.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.igar15.rest_voting_system.model.User;
import ru.igar15.rest_voting_system.repository.UserRepository;
import ru.igar15.rest_voting_system.util.exception.NotFoundException;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return userRepository.save(user);
    }

    public void delete(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found user with id=" + id));
        userRepository.delete(user);
    }

    public User get(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Not found user with id=" + id));
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Not found user with email=" + email));
    }

    public List<User> getAll() {
        return userRepository.findAllByOrderByNameAscEmailAsc();
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        get(user.getId());
        userRepository.save(user);
    }
}
