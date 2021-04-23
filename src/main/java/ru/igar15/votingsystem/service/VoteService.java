package ru.igar15.votingsystem.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.igar15.votingsystem.model.Restaurant;
import ru.igar15.votingsystem.model.User;
import ru.igar15.votingsystem.model.Vote;
import ru.igar15.votingsystem.repository.UserRepository;
import ru.igar15.votingsystem.repository.VoteRepository;
import ru.igar15.votingsystem.util.exception.VoteUpdateForbiddenException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class VoteService {
    private static final LocalTime BOUNDARY_TIME = LocalTime.of(11, 0);

    private final VoteRepository voteRepository;
    private final RestaurantService restaurantService;
    private final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, RestaurantService restaurantService, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantService = restaurantService;
        this.userRepository = userRepository;
    }

    @Transactional
    public Vote registerVote(int userId, int restaurantId, LocalDate date, LocalTime time) {
        Optional<Vote> voteOptional = voteRepository.findByDate(date, userId);
        return voteOptional.map(vote -> update(vote, restaurantId, time)).orElseGet(() -> create(userId, restaurantId, date));
    }

    private Vote create(int userId, int restaurantId, LocalDate date) {
        Vote vote = new Vote(null, date);
        Restaurant restaurant = restaurantService.get(restaurantId);
        User user = userRepository.getOne(userId);
        vote.setRestaurant(restaurant);
        vote.setUser(user);
        return voteRepository.save(vote);
    }

    private Vote update(Vote vote, int restaurantId, LocalTime time) {
        if (isUpdateForbidden(time)) {
            throw new VoteUpdateForbiddenException("It is too late to change your vote");
        }
        Restaurant restaurant = restaurantService.get(restaurantId);
        vote.setRestaurant(restaurant);
        return voteRepository.save(vote);
    }

    private boolean isUpdateForbidden(LocalTime time) {
        return time.isAfter(BOUNDARY_TIME);
    }
}