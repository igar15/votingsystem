package ru.igar15.rest_voting_system.service;

import org.springframework.stereotype.Service;
import ru.igar15.rest_voting_system.model.Restaurant;
import ru.igar15.rest_voting_system.model.User;
import ru.igar15.rest_voting_system.model.Vote;
import ru.igar15.rest_voting_system.repository.RestaurantRepository;
import ru.igar15.rest_voting_system.repository.UserRepository;
import ru.igar15.rest_voting_system.repository.VoteRepository;
import ru.igar15.rest_voting_system.util.exception.VoteUpdateForbiddenException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    public Vote registerVote(int restaurantId, int userId, LocalTime time) {
        Optional<Vote> todayVote = voteRepository.findByDateAndUser_Id(LocalDate.now(), userId);
        return todayVote.map(vote -> update(vote, restaurantId, time)).orElseGet(() -> create(restaurantId, userId));
    }

    private Vote create(int restaurantId, int userId) {
        Vote vote = new Vote(LocalDate.now());
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        User user = userRepository.getOne(userId);
        vote.setRestaurant(restaurant);
        vote.setUser(user);
        return voteRepository.save(vote);
    }

    private Vote update(Vote vote, int restaurantId, LocalTime time) {
        if (isUpdateForbidden(time)) {
            throw new VoteUpdateForbiddenException("It is too late to change your vote");
        }
        Restaurant restaurant = restaurantRepository.getOne(restaurantId);
        vote.setRestaurant(restaurant);
        return voteRepository.save(vote);
    }

    private boolean isUpdateForbidden(LocalTime time) {
        return time.isAfter(LocalTime.of(11, 0));
    }
}